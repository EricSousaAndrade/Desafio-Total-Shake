package br.com.desafio.totalshake.application.controller;

import br.com.desafio.totalshake.application.controller.request.ItemPedidoDTO;
import br.com.desafio.totalshake.application.controller.request.PedidoDTOPost;
import br.com.desafio.totalshake.domain.model.ItemPedido;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class PedidoControllerIntegrationTest {

    private final String PEDIDO_URI = "/pedidos";

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

    @Test
    @Transactional
    public void deve_criarUmPedidoComItens_e_devolverDTOPedidoCriado() throws Exception{
        var pedidoRequest = new PedidoDTOPost();
        pedidoRequest.setItens(new ArrayList<>(Arrays.asList(
                new ItemPedidoDTO("Coca-cola", 2),
                new ItemPedidoDTO("Trakinas", 2)
        )));

        String pedidoRequestJson = objectMapper.writeValueAsString(pedidoRequest);

        mockMvc.perform(post(PEDIDO_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pedidoRequestJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("dataHora").isNotEmpty())
                .andExpect(jsonPath("itens").isNotEmpty());

        List<Pedido> pedidoCriado = pedidoRepository.findAll();
        assertEquals(2, pedidoCriado.get(0).getItens().size());
        assertEquals(Status.REALIZADO, pedidoCriado.get(0).getStatus());
    }

    @Test
    @Transactional
    public void deve_buscarUmPedidoComSucesso_e_devolverDTOPedido() throws Exception{
        var pedido = new Pedido();
        pedido.setItens(new ArrayList<>(List.of(new ItemPedido("Coca-cola", 2))));
        pedido.setDataHora(LocalDateTime.of(LocalDate.of(2022,1,3), LocalTime.now()));
        pedido.setStatus(Status.REALIZADO);

        var pedidoSalvo = pedidoRepository.save(pedido);

        mockMvc.perform(get(PEDIDO_URI +"/"+pedidoSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("dataHora").isNotEmpty())
                .andExpect(jsonPath("itens").isNotEmpty());

        List<Pedido> pedidoCriado = pedidoRepository.findAll();
        assertEquals(1, pedidoCriado.get(0).getItens().size());
        assertEquals(Status.REALIZADO, pedidoCriado.get(0).getStatus());
    }

    @Test
    @Transactional
    public void deve_adicionarUmItemNoPedidoComSucesso_e_devolverDTOPedido() throws Exception{
        var pedido = new Pedido();
        pedido.setItens(new ArrayList<>(List.of(new ItemPedido("Coca-cola", 2))));
        pedido.setDataHora(LocalDateTime.of(LocalDate.of(2022,1,3), LocalTime.now()));
        pedido.setStatus(Status.REALIZADO);

        var pedidoSalvo = pedidoRepository.save(pedido);

        var itemPedido = new ItemPedido("Arroz", 4);
        String itemPedidoRequest = objectMapper.writeValueAsString(itemPedido);

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

        Pedido pedidoCriado = pedidoRepository.findAll().get(0);
        List<ItemPedido> itens = pedidoCriado.getItens();

        assertEquals(2, pedidoCriado.getItens().size());
        assertEquals(Status.REALIZADO, pedidoCriado.getStatus());
        assertEquals("Arroz",itens.get(1).getDescricao());
        assertEquals(4,itens.get(1).getQuantidade());
    }

    @Test
    @Transactional
    public void deve_cancelarUmPedido_comSucesso() throws Exception{
        var pedido = new Pedido();
        pedido.setItens(new ArrayList<>(List.of(new ItemPedido("Coca-cola", 2))));
        pedido.setDataHora(LocalDateTime.of(LocalDate.of(2022,1,3), LocalTime.now()));
        pedido.setStatus(Status.CONFIRMADO);

        var pedidoSalvo = pedidoRepository.save(pedido);

        mockMvc.perform(put(PEDIDO_URI +"/cancelar/"+pedidoSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("dataHora").isNotEmpty())
                .andExpect(jsonPath("status").value(Status.CANCELADO.name()))
                .andExpect(jsonPath("itens").isNotEmpty());

        List<Pedido> pedidoCriado = pedidoRepository.findAll();
        assertEquals(1, pedidoCriado.get(0).getItens().size());
        assertEquals(Status.CANCELADO, pedidoCriado.get(0).getStatus());
    }

    @Test
    @Transactional
    public void deve_acrescentarQuantidadeNoItemDoPedido_comSucesso() throws Exception{
        var pedido = new Pedido();
        pedido.setItens(new ArrayList<>(List.of(new ItemPedido("Coca-cola", 2))));
        pedido.setDataHora(LocalDateTime.of(LocalDate.of(2022,1,3), LocalTime.now()));
        pedido.setStatus(Status.CONFIRMADO);

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
                .andExpect(jsonPath("itens[0].descricao").value("Coca-cola"))
                .andExpect(jsonPath("itens[0].quantidade").value(5));


        Pedido pedidoCriado = pedidoRepository.findAll().get(0);
        var itemDoPedidoCriado = pedidoCriado.getItens().get(0);
        assertEquals(1, pedidoCriado.getItens().size());
        assertEquals(5, itemDoPedidoCriado.getQuantidade());
        assertEquals(Status.CONFIRMADO, pedidoCriado.getStatus());
    }

    @Test
    @Transactional
    public void deve_reduzirQuantidadeNoItemDoPedido_comSucesso() throws Exception{
        var pedido = new Pedido();
        pedido.setItens(new ArrayList<>(List.of(new ItemPedido("Coca-cola", 2))));
        pedido.setDataHora(LocalDateTime.of(LocalDate.of(2022,1,3), LocalTime.now()));
        pedido.setStatus(Status.CONFIRMADO);

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
                .andExpect(jsonPath("itens[0].descricao").value("Coca-cola"))
                .andExpect(jsonPath("itens[0].quantidade").value(1));


        Pedido pedidoCriado = pedidoRepository.findAll().get(0);
        var itemDoPedidoCriado = pedidoCriado.getItens().get(0);
        assertEquals(1, pedidoCriado.getItens().size());
        assertEquals(1, itemDoPedidoCriado.getQuantidade());
        assertEquals(Status.CONFIRMADO, pedidoCriado.getStatus());
    }
}
