package br.com.desafio.totalshake.domain.service.crud;

import br.com.desafio.totalshake.application.controller.request.ItemPedidoDTO;
import br.com.desafio.totalshake.application.controller.request.PedidoDTOPost;
import br.com.desafio.totalshake.application.errors.exceptions.PedidoInexistenteException;
import br.com.desafio.totalshake.builders.PedidoBuilder;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.repository.PedidoRepository;
import br.com.desafio.totalshake.domain.service.crud.PedidoCrudService;
import br.com.desafio.totalshake.domain.service.state.impl.CanceladoImpl;
import br.com.desafio.totalshake.domain.service.state.impl.CriadoImpl;
import br.com.desafio.totalshake.domain.service.state.impl.RealizadoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoCrudServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    PedidoCrudService pedidoService;

    @BeforeEach
    public void setUp(){
        pedidoService = new PedidoCrudService(pedidoRepository);
    }

    @Nested
    class TestesDeAcoesDoPedido{

        @Test
        public void deve_salvarOPedidoESeusItensE_setarStatusComoCriado_aoSalvarUmPedido(){
            var pedidoDTOPost = umDtoPedidoPostComDoisItens();
            var pedidoEntidade = pedidoDTOPost.toPedidoModel();

            ArgumentCaptor<Pedido> pedidoCapturadoNoRetornoDeSave = ArgumentCaptor.forClass(Pedido.class);
            when(pedidoRepository.save(pedidoEntidade)).thenReturn(pedidoEntidade);

            pedidoService.criarPedido(pedidoDTOPost);

            verify(pedidoRepository, times(1)).save(pedidoCapturadoNoRetornoDeSave.capture());

            var pedidoCapturado = pedidoCapturadoNoRetornoDeSave.getValue();

            assertAll(
                    () -> assertEquals(Status.CRIADO, pedidoCapturado.getStatus()),
                    () -> assertTrue(pedidoCapturado.getEstadoPedido() instanceof CriadoImpl),
                    () -> assertNotNull(pedidoCapturado.getDataHoraStatus().getDataHoraCriado()),
                    () -> assertEquals(2, pedidoCapturado.getItens().size())
            );
        }

        @Test
        public void deve_setarOStatusComoRealizado_aoRealizarUmPedido(){

            var pedido = PedidoBuilder.umPedido().comEstadoCriado().build();
            ArgumentCaptor<Pedido> pedidoCapturadoNoRetornoDeSave = ArgumentCaptor.forClass(Pedido.class);

            when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
            when(pedidoRepository.save(pedido)).thenReturn(pedido);

            pedidoService.realizarPedido(1L);

            verify(pedidoRepository, times(1)).save(pedidoCapturadoNoRetornoDeSave.capture());

            var pedidoCapturado = pedidoCapturadoNoRetornoDeSave.getValue();

            assertAll(
                    () -> assertEquals(Status.REALIZADO, pedidoCapturado.getStatus()),
                    () -> assertTrue(pedidoCapturado.getEstadoPedido() instanceof RealizadoImpl),
                    () -> assertNotNull(pedidoCapturado.getDataHoraStatus().getDataHoraRealizado())
            );
        }

        @Test
        public void deve_setarOStatusComoCancelado_aoCancelarUmPedido(){
            var pedido = PedidoBuilder.umPedido().comEstadoCriado().comUmItemPedido().build();
            ArgumentCaptor<Pedido> pedidoCapturadoNoRetornoDeSave = ArgumentCaptor.forClass(Pedido.class);

            when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
            when(pedidoRepository.save(pedido)).thenReturn(pedido);

            pedidoService.cancelarPedido(1L);

            verify(pedidoRepository, times(1)).save(pedidoCapturadoNoRetornoDeSave.capture());

            var pedidoCapturado = pedidoCapturadoNoRetornoDeSave.getValue();

            assertAll(
                    () -> assertEquals(Status.CANCELADO, pedidoCapturado.getStatus()),
                    () -> assertTrue(pedidoCapturado.getEstadoPedido() instanceof CanceladoImpl),
                    () -> assertNotNull(pedidoCapturado.getDataHoraStatus().getDataHoraCancelado())
            );
        }

        @Test
        public void deve_fazerARelacaoEntrePedidoEItem_corretamente(){

            var itemPedidoDto = new ItemPedidoDTO("feijao",2);
            var pedidoSemItens = PedidoBuilder.umPedido().build();

            when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoSemItens));
            when(pedidoRepository.save(pedidoSemItens)).thenReturn(pedidoSemItens);

            var pedidoSalvo = pedidoService.adicionarItemNoPedido(1L, itemPedidoDto);
            var itemPedido = pedidoSemItens.getItens().get(0);

            assertAll(
                    () ->  assertEquals(1,pedidoSalvo.getItens().size()),
                    () ->  assertTrue(pedidoSalvo.getItens().contains(itemPedido))
            );

            verify(pedidoRepository, times(1)).save(pedidoSemItens);
        }

        @Test
        public void deve_retornarUmPedido_aoBuscarPorId(){
            var pedido = PedidoBuilder.umPedido().comUmItemPedido().build();

            when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

            var pedidoEncontrado = pedidoService.buscarPedidoPorId(1L);

            assertAll(
                    () -> assertEquals(1 ,pedidoEncontrado.getItens().size()),
                    () -> assertEquals(1L, pedidoEncontrado.getId())
            );
            verify(pedidoRepository, times(1)).findById(1L);
        }

        @Test
        public void deve_lancarExcecaoAoBuscarUmPedido_quandoInexistente(){

            when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(
                    PedidoInexistenteException.class,
                    () -> pedidoService.buscarPedidoPorId(1L)
            );

            verify(pedidoRepository, times(1)).findById(1L);
        }

        private PedidoDTOPost umDtoPedidoPostComDoisItens() {
            PedidoDTOPost pedidoDto = new PedidoDTOPost();
            List<ItemPedidoDTO> itensPedidoDto = new ArrayList<>(Arrays.asList(
                    new ItemPedidoDTO("Coca cola", 2),
                    new ItemPedidoDTO("Arroz frito",1)
            ));
            pedidoDto.setItens(itensPedidoDto);

            return pedidoDto;
        }

    }

    @Nested
    class TestesDeAcoesDoItemPedido{

        @Test
        public void deve_incrementarAQuantidadeDeUm_itemPedido_corretamente(){
            var pedidoComUmItem = PedidoBuilder.umPedido().comUmItemPedido().build();
            var idPedido = pedidoComUmItem.getId();
            var idItemPedido = pedidoComUmItem.getItens().get(0).getId();

            when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedidoComUmItem));
            when(pedidoRepository.save(pedidoComUmItem)).thenReturn(pedidoComUmItem);

            var pedidoAcrescentado = pedidoService.acrescentarItem(idPedido, idItemPedido, 3);
            var itemPedidoAcrescentado = pedidoAcrescentado.getItens().get(0);

            assertEquals(5, itemPedidoAcrescentado.getQuantidade());
        }

        @Test
        public void deve_reduzirAQuantidadeDeUm_itemPedido_corretamente(){

            var pedidoComUmItem = PedidoBuilder.umPedido().comUmItemPedido().build();
            var idPedido = pedidoComUmItem.getId();
            var idItemPedido = pedidoComUmItem.getItens().get(0).getId();

            when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedidoComUmItem));
            when(pedidoRepository.save(pedidoComUmItem)).thenReturn(pedidoComUmItem);


            var pedidoReduzido = pedidoService.reduzirQuantidadeItem(idPedido, idItemPedido, 1);
            var itemPedidoReduzido = pedidoReduzido.getItens().get(0);

            assertEquals(1, itemPedidoReduzido.getQuantidade());
        }

        @Test
        public void deve_removerUm_itemPedido_quandoQtdRestanteForMenorIgual0(){
            var pedidoComUmItem = PedidoBuilder.umPedido().comUmItemPedido().build();
            var idPedido = pedidoComUmItem.getId();
            var idItemPedido = pedidoComUmItem.getItens().get(0).getId();

            when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedidoComUmItem));
            when(pedidoRepository.save(pedidoComUmItem)).thenReturn(pedidoComUmItem);

            var pedidoSemItens = pedidoService.reduzirQuantidadeItem(idPedido, idItemPedido, 2);

            assertEquals(0, pedidoSemItens.getItens().size());
        }
    }
}
