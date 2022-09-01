package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PagoImplTest {

    Pedido pedidoEmEstadoDePago;

    @BeforeEach
    public void setUp(){
        pedidoEmEstadoDePago = new Pedido();
        pedidoEmEstadoDePago.setStatus(Status.PAGO);
        pedidoEmEstadoDePago.setEstadoPedido(new PagoImpl(pedidoEmEstadoDePago));
    }

    @Nested
    class TestesPossivelAlteracaoDeEstado{
        @Test
        void deve_setar_mudandoStatusParaConfirmado_setandoEstadoParaConfirmado() {
            pedidoEmEstadoDePago.getEstadoPedido().confirmarPedido();

            assertAll(
                    () -> assertTrue(pedidoEmEstadoDePago.getEstadoPedido() instanceof ConfirmadoImpl),
                    () -> assertEquals(Status.CONFIRMADO, pedidoEmEstadoDePago.getStatus())
            );
        }

        @Test
        void deve_naoAutorizarPedido_mudandoStatusParaNaoAutorizado_setandoEstadoParaNauAutorizado() {
            pedidoEmEstadoDePago.getEstadoPedido().naoAutorizarPedido();

            assertAll(
                    () -> assertTrue(pedidoEmEstadoDePago.getEstadoPedido() instanceof NaoAutorizadoImpl),
                    () -> assertEquals(Status.NAO_AUTORIZADO, pedidoEmEstadoDePago.getStatus())
            );
        }
    }

    @Nested
    class TestesExcecaoDeAlteracaoImpossivelDeEstado{

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarPagarPedidoJaPago() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDePago.getEstadoPedido().pagarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarRealizarOPedido_emEstadoDeConfirmado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDePago.getEstadoPedido().realizarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarDeixarPedidoPronto_EmEstadoDePago() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDePago.getEstadoPedido().pedidoPronto()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarSetarPedidoSaiuParaEntregar_EmEstadoDePago(){
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDePago.getEstadoPedido().pedidoSaiuParaEntrega()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarSetarPedidoComoEntregue_EmEstadoDePago() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDePago.getEstadoPedido().pedidoEntregue()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarCancelarPedido_EmEstadoDePago() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDePago.getEstadoPedido().cancelarPedido()
            );
        }
    }

}