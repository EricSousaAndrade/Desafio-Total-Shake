package br.com.desafio.totalshake.domain.service.state.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.state.impl.CanceladoImpl;
import br.com.desafio.totalshake.domain.service.state.impl.PagoImpl;
import br.com.desafio.totalshake.domain.service.state.impl.RealizadoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RealizadoImplTest {

    Pedido pedidoEmEstadoDeRealizado;

    @BeforeEach
    public void setUp(){
        pedidoEmEstadoDeRealizado = new Pedido();
        pedidoEmEstadoDeRealizado.setStatus(Status.REALIZADO);
        pedidoEmEstadoDeRealizado.setEstadoPedido(new RealizadoImpl(pedidoEmEstadoDeRealizado));
    }

    @Nested
    class TestesPossivelAlteracaoDeEstado{
        @Test
        void deve_pagarOPedido_mudandoStatusParaPago_setandoEstadoParaPago() {
            pedidoEmEstadoDeRealizado.getEstadoPedido().pagarPedido();

            assertAll(
                    () -> assertTrue(pedidoEmEstadoDeRealizado.getEstadoPedido() instanceof PagoImpl),
                    () -> assertEquals(Status.PAGO, pedidoEmEstadoDeRealizado.getStatus())
            );
        }

        @Test
        void deve_cancelarPedido_mudandoStatusParaCancelado_setandoEstadoParaCancelado() {
            pedidoEmEstadoDeRealizado.getEstadoPedido().cancelarPedido();

            assertAll(
                    () -> assertTrue(pedidoEmEstadoDeRealizado.getEstadoPedido() instanceof CanceladoImpl),
                    () -> assertEquals(Status.CANCELADO, pedidoEmEstadoDeRealizado.getStatus())
            );
        }
    }

    @Nested
    class TestesExcecaoDeAlteracaoImpossivelDeEstado{
        @Test
        void deve_lancarExcecaoDeStatusAo_tentarRealizarOPedidoJaRealizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeRealizado.getEstadoPedido().realizarPedido()
            );
        }


        @Test
        void deve_lancarExcecaoDeStatusAo_TentarConfirmarPedido_EmEstadoDeRealizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeRealizado.getEstadoPedido().confirmarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarDeixarPedidoPronto_EmEstadoDeRealizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeRealizado.getEstadoPedido().pedidoPronto()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarSetarPedidoSaiuParaEntregar_EmEstadoDeRealizado(){
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeRealizado.getEstadoPedido().pedidoSaiuParaEntrega()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarSetarPedidoComoEntregue_EmEstadoDeRealizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeRealizado.getEstadoPedido().pedidoEntregue()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarNaoAutorizarPedido_EmEstadoDeRealizado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeRealizado.getEstadoPedido().naoAutorizarPedido()
            );
        }
    }
}