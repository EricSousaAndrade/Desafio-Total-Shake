package br.com.desafio.totalshake.domain.service.state.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.state.impl.NaoAutorizadoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NaoAutorizadoImplTest {

    Pedido pedidoEmEstadoDeNaoAutorizado;

    @BeforeEach
    public void setUp(){
        pedidoEmEstadoDeNaoAutorizado = new Pedido();
        pedidoEmEstadoDeNaoAutorizado.setStatus(Status.NAO_AUTORIZADO);
        pedidoEmEstadoDeNaoAutorizado.setEstadoPedido(new NaoAutorizadoImpl(pedidoEmEstadoDeNaoAutorizado));
    }

    @Nested
    class TestesExcecaoDeAlteracaoImpossivelDeEstado{

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarPagarPedidoNaoAutorizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeNaoAutorizado.getEstadoPedido().pagarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarSetarEntregueEmPedidoNaoAutorizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeNaoAutorizado.getEstadoPedido().pedidoEntregue()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_setarPedidoNaoAutorizado_ParaEstadoParaEntregue() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeNaoAutorizado.getEstadoPedido().pedidoSaiuParaEntrega()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarCancelarPedidoNaoAutorizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeNaoAutorizado.getEstadoPedido().cancelarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarRealizarPedidoNaoAutorizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeNaoAutorizado.getEstadoPedido().realizarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarConfirmarPedidoNaoAutorizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeNaoAutorizado.getEstadoPedido().confirmarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarSetarPedidoQueSaiuParaEntrega_emPedidoNaoAutorizado(){
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeNaoAutorizado.getEstadoPedido().pedidoPronto()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarNaoAutorizarPedido_emPedidoNaoAutorizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeNaoAutorizado.getEstadoPedido().naoAutorizarPedido()
            );
        }
    }

}