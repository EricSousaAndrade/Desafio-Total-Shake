package br.com.desafio.totalshake.application.controller;

import br.com.desafio.totalshake.application.controller.request.ItemPedidoDTO;
import br.com.desafio.totalshake.application.controller.request.PedidoDTOPost;
import br.com.desafio.totalshake.application.errors.CodInternoErroApi;
import br.com.desafio.totalshake.builders.PedidoBuilder;
import br.com.desafio.totalshake.domain.model.ItemPedido;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.repository.PedidoRepository;
import br.com.desafio.totalshake.domain.service.state.impl.CanceladoImpl;
import br.com.desafio.totalshake.domain.service.state.impl.CriadoImpl;
import br.com.desafio.totalshake.domain.service.state.impl.RealizadoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class PedidoControllerIntegrationTest {

    private final String PEDIDO_URI = "/v1/pedidos";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void cleanDatabase(){
      pedidoRepository.deleteAll();
    }

    @AfterEach
    public void tearDown(){
       pedidoRepository.deleteAll();
    }

    @Nested
    class TestesCriacaoDePedido{

        @Test
        @Transactional
        public void deve_criarUmPedidoComUmItem_e_devolverDTOPedidoStatusCriado() throws Exception{
            var pedidoRequest = umPedidoRequestValido();

            String pedidoRequestJson = objectMapper.writeValueAsString(pedidoRequest);

            mockMvc.perform(post(PEDIDO_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(pedidoRequestJson)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("dataHora").isNotEmpty())
                    .andExpect(jsonPath("status").value(Status.CRIADO.name()))
                    .andExpect(jsonPath("itens").isNotEmpty());

            var pedidoCriado = pedidoRepository.findAll().get(0);

            assertAll(
                    () -> assertEquals(1, pedidoCriado.getItens().size()),
                    () -> assertTrue(pedidoCriado.getEstadoPedido() instanceof CriadoImpl),
                    () -> assertEquals(Status.CRIADO, pedidoCriado.getStatus()),
                    () -> assertNotNull(pedidoCriado.getDataHoraStatus().getDataHoraCriado())
            );
        }

        @Test
        @Transactional
        public void deve_lancarExcecaoDeArgumentosInvalidos_e_devolverErrosParaOCliente() throws Exception{
            var pedidoRequestInvalido = umPedidoComItensInvalidos();

            String pedidoRequestJson = objectMapper.writeValueAsString(pedidoRequestInvalido);

            mockMvc.perform(post(PEDIDO_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(pedidoRequestJson)
                    )
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("mensagem").value(CodInternoErroApi.AP001.getMensagem()))
                    .andExpect(jsonPath("codInterno").value(CodInternoErroApi.AP001.getCodigo()))
                    .andExpect(jsonPath("erros").isNotEmpty());

            int registrosDePedidoNoDatabase = pedidoRepository.findAll().size();
            assertEquals(0, registrosDePedidoNoDatabase);
        }

        private PedidoDTOPost umPedidoComItensInvalidos() {
            var pedidoDto = new PedidoDTOPost();
            pedidoDto.setItens(new ArrayList<>(Arrays.asList(
                    new ItemPedidoDTO(" ", 2),
                    new ItemPedidoDTO("Trakinas", -1)
            )));

            return pedidoDto;
        }

        private PedidoDTOPost umPedidoRequestValido() {
            var pedidoDto = new PedidoDTOPost();
            pedidoDto.setItens(new ArrayList<>(Arrays.asList(
                    new ItemPedidoDTO("Trakinas", 1)
            )));

            return pedidoDto;
        }

    }

    @Nested
    class TestesCancelarPedido{

        @Test
        @Transactional
        public void deve_alterarUmPedidoExistenteParaCanceladoE_devolverDTOParaOCliente() throws Exception{
            var pedido = PedidoBuilder.umPedido().comEstadoCriado().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);

            mockMvc.perform(put(PEDIDO_URI +"/"+pedidoSalvo.getId()+"/cancelar"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("dataHora").isNotEmpty())
                    .andExpect(jsonPath("status").value(Status.CANCELADO.name()))
                    .andExpect(jsonPath("itens").isNotEmpty())
                    .andExpect(jsonPath("dataHoraStatus").isNotEmpty());

            Pedido pedidoAlterado = pedidoRepository.findAll().get(0);

            assertAll(
                    () -> assertEquals(1, pedidoAlterado.getItens().size()),
                    () -> assertEquals(Status.CANCELADO, pedidoAlterado.getStatus()),
                    () -> assertTrue(pedidoAlterado.getEstadoPedido() instanceof CanceladoImpl),
                    () -> assertNotNull(pedidoAlterado.getDataHoraStatus().getDataHoraCancelado())
            );
        }

        @Test
        @Transactional
        // todo: Fazer parametrizável com todos os status diferentes de CRIADO e REALIZADO como argumento do teste
        public void deve_lancarExcecaoDeStatus_aoCancelarPedidoComStatus_devolverDTODeErroParaOCliente() throws Exception{

            var pedido = PedidoBuilder.umPedido().comEstadoPago().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);

            mockMvc.perform(put(PEDIDO_URI +"/"+pedidoSalvo.getId()+"/cancelar"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("mensagem").value(CodInternoErroApi.AP301.getMensagem()))
                    .andExpect(jsonPath("codInterno").value(CodInternoErroApi.AP301.getCodigo()))
                    .andExpect(jsonPath("statusPedido").value(pedido.getStatus().name()));

            Pedido pedidoInalterado = pedidoRepository.findAll().get(0);
            assertFalse(pedidoInalterado.getEstadoPedido() instanceof CanceladoImpl);
            assertNotEquals(Status.CANCELADO, pedidoInalterado.getStatus());
        }
    }

    @Nested
    class TestesRealizarPedido{

        @Test
        @Transactional
        public void deve_alterarUmPedidoExistenteParaRealizadoE_devolverDTOParaOCliente() throws Exception{

            var pedido = PedidoBuilder.umPedido().comEstadoCriado().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);

            mockMvc.perform(put(PEDIDO_URI+"/"+ pedidoSalvo.getId() +"/realizar"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("dataHora").isNotEmpty())
                    .andExpect(jsonPath("status").value(Status.REALIZADO.name()))
                    .andExpect(jsonPath("itens").isNotEmpty())
                    .andExpect(jsonPath("dataHoraStatus").isNotEmpty());

            var pedidoAlterado = pedidoRepository.findAll().get(0);
            assertAll(
                    () -> assertEquals(Status.REALIZADO, pedidoAlterado.getStatus()),
                    () -> assertTrue(pedidoAlterado.getEstadoPedido() instanceof RealizadoImpl),
                    () -> assertNotNull(pedidoAlterado.getDataHoraStatus().getDataHoraRealizado())
            );
        }

        @Test
        @Transactional
        // todo: Fazer parametrizável com todos os status diferentes de Criado como argumento do teste
        public void deve_lancarExcecaoDeStatus_aoRealizarPedidoComStatusDiferenteDeCriado_devolverDTODeErroParaOCliente() throws Exception{

            var pedido = PedidoBuilder.umPedido().comEstadoPago().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);

            mockMvc.perform(put(PEDIDO_URI +"/"+pedidoSalvo.getId()+"/realizar"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("mensagem").value(CodInternoErroApi.AP301.getMensagem()))
                    .andExpect(jsonPath("codInterno").value(CodInternoErroApi.AP301.getCodigo()))
                    .andExpect(jsonPath("statusPedido").value(pedido.getStatus().name()));

            Pedido pedidoInalterado = pedidoRepository.findAll().get(0);
            assertFalse(pedidoInalterado.getEstadoPedido() instanceof RealizadoImpl);
            assertNotEquals(Status.REALIZADO, pedidoInalterado.getStatus());
        }

    }

    @Nested
    class TestesBuscaDePedido{
        @Test
        @Transactional
        public void deve_buscarUmPedidoComSucesso_e_devolverDTOPedido() throws Exception{
            var pedido = PedidoBuilder.umPedido().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);

            mockMvc.perform(get(PEDIDO_URI +"/"+pedidoSalvo.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("itens").isNotEmpty());

            Pedido pedidoCriado = pedidoRepository.findAll().get(0);
            assertEquals(1, pedidoCriado.getItens().size());
        }

        @Test
        @Transactional
        public void deve_lancarExcecaoDePedidoInexistente_e_devolverDTODeErroParaOCliente() throws Exception{

            mockMvc.perform(get(PEDIDO_URI +"/"+1L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("mensagem").value(CodInternoErroApi.AP201.getMensagem()))
                    .andExpect(jsonPath("codInterno").value(CodInternoErroApi.AP201.getCodigo()));

            int registrosDePedidoNoDatabase = pedidoRepository.findAll().size();
            assertEquals(0, registrosDePedidoNoDatabase);
        }

    }


    @Nested
    class TestesDeAlteracaoNosItensDoPedido{

        @Test
        @Transactional
        public void deve_adicionarUmItemNoPedidoComSucesso_e_devolverDTOPedido() throws Exception{
            var pedido = PedidoBuilder.umPedido().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);

            var novoItemPedido = new ItemPedido("Arroz", 4);

            String itemPedidoRequest = objectMapper.writeValueAsString(novoItemPedido);

            mockMvc.perform(post(PEDIDO_URI +"/"+pedidoSalvo.getId()+"/adicionar-item")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(itemPedidoRequest)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("dataHora").isNotEmpty())
                    .andExpect(jsonPath("itens").isNotEmpty())
                    .andExpect(jsonPath("itens[1].descricao").value("Arroz"))
                    .andExpect(jsonPath("itens[1].quantidade").value(4));

            Pedido pedidoAlterado = pedidoRepository.findAll().get(0);
            ItemPedido novoItemAdicionado = pedidoAlterado.getItens().get(1);

            assertAll(
                    () -> assertEquals(2, pedidoAlterado.getItens().size()),
                    () -> assertEquals("Arroz",novoItemAdicionado.getDescricao()),
                    () -> assertEquals(4,novoItemAdicionado.getQuantidade())
            );
        }

        @Test
        @Transactional
        public void deve_acrescentarQuantidadeNoItemDoPedido_comSucesso() throws Exception{
            var pedido = PedidoBuilder.umPedido().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);

            var itemDoPedido = pedidoSalvo.getItens().get(0);

            mockMvc.perform(
                            put(PEDIDO_URI +"/" +pedidoSalvo.getId()
                                    +"/itens/" +itemDoPedido.getId() +"/acrescentar/"+3)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("dataHora").isNotEmpty())
                    .andExpect(jsonPath("itens").isNotEmpty())
                    .andExpect(jsonPath("itens[0].descricao").value("Coca cola"))
                    .andExpect(jsonPath("itens[0].quantidade").value(5));


            var pedidoAtualizado = pedidoRepository.findAll().get(0);
            var itemDoPedidoAlterado = pedidoAtualizado.getItens().get(0);
            assertAll(
                    () -> assertEquals(1, pedidoAtualizado.getItens().size()),
                    () ->  assertEquals(5, itemDoPedidoAlterado.getQuantidade())
            );
        }

        @Test
        @Transactional
        public void deve_reduzirQuantidadeNoItemDoPedido_comSucesso() throws Exception{
            var pedido = PedidoBuilder.umPedido().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);
            var itemDoPedido = pedidoSalvo.getItens().get(0);

            mockMvc.perform(
                            put(PEDIDO_URI +"/" +pedidoSalvo.getId()
                                    +"/itens/" +itemDoPedido.getId() +"/reduzir/"+1)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").isNotEmpty())
                    .andExpect(jsonPath("dataHora").isNotEmpty())
                    .andExpect(jsonPath("itens").isNotEmpty())
                    .andExpect(jsonPath("itens[0].descricao").value("Coca cola"))
                    .andExpect(jsonPath("itens[0].quantidade").value(1));


            Pedido pedidoCriado = pedidoRepository.findAll().get(0);
            var itemDoPedidoCriado = pedidoCriado.getItens().get(0);
            assertAll(
                    () -> assertEquals(1, pedidoCriado.getItens().size()),
                    () -> assertEquals(1, itemDoPedidoCriado.getQuantidade())
            );
        }

        @Test
        @Transactional
        public void deve_lancarExcecaoDeQuantidadeInvalida_e_devolverDTODeErroParaOCliente() throws Exception{
            var pedido = PedidoBuilder.umPedido().comUmItemPedido().build();

            var pedidoSalvo = pedidoRepository.save(pedido);
            var itemDoPedido = pedidoSalvo.getItens().get(0);
            var quantidadeInvalida = -2;

            mockMvc.perform(
                            put(PEDIDO_URI +"/" +pedidoSalvo.getId()
                                    +"/itens/" +itemDoPedido.getId() +"/reduzir/"+quantidadeInvalida)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("mensagem").value(CodInternoErroApi.AP203.getMensagem()))
                    .andExpect(jsonPath("codInterno").value(CodInternoErroApi.AP203.getCodigo()));

            var pedidoDB = pedidoRepository.findAll().get(0);
            assertEquals(2, pedidoDB.getItens().get(0).getQuantidade());
        }

        @Test
        @Transactional
        public void deve_lancarExcecaoDeItemInexistente_e_devolverDTODeErroParaOCliente() throws Exception{
            var pedido = PedidoBuilder.umPedido().build();
            var pedidoSalvo = pedidoRepository.save(pedido);

            mockMvc.perform(
                            put(PEDIDO_URI +"/" +pedidoSalvo.getId()
                                    +"/itens/" +1L +"/acrescentar/"+1)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("mensagem").value(CodInternoErroApi.AP202.getMensagem()))
                    .andExpect(jsonPath("codInterno").value(CodInternoErroApi.AP202.getCodigo()));


            var pedidoSemItens = pedidoRepository.findAll().get(0);
            assertEquals(0, pedidoSemItens.getItens().size());
        }
    }

}
