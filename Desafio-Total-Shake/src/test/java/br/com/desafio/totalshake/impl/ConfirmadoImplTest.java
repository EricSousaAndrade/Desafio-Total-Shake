package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmadoImplTest {

    Pedido pedidoEmEstadoDeConfirmado;

    @BeforeEach
    public void setUp(){
        pedidoEmEstadoDeConfirmado = new Pedido();
        pedidoEmEstadoDeConfirmado.setStatus(Status.CONFIRMADO);
        pedidoEmEstadoDeConfirmado.setEstadoPedido(new ConfirmadoImpl(pedidoEmEstadoDeConfirmado));
    }

    @Nested
    class TestesPossivelAlteracaoDeEstado{
        @Test
        void deve_mudarStatusDoPedidoParaPronto_setandoEstadoParaPronto() {
            pedidoEmEstadoDeConfirmado.getEstadoPedido().pedidoPronto();

            assertAll(
                    () -> assertTrue(pedidoEmEstadoDeConfirmado.getEstadoPedido() instanceof ProntoImpl),
                    () -> assertEquals(Status.PRONTO, pedidoEmEstadoDeConfirmado.getStatus())
            );
        }
    }

    @Nested
    class TestesExcecaoDeAlteracaoImpossivelDeEstado{

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarPagarPedidoConfirmado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeConfirmado.getEstadoPedido().pagarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarCancelarPedidoConfirmado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeConfirmado.getEstadoPedido().cancelarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarRealizarPedidoConfirmado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeConfirmado.getEstadoPedido().realizarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarConfirmarPedidoJaConfirmado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeConfirmado.getEstadoPedido().confirmarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarSetarPedidoSaiuParaEntregar_EmEstadoDeConfirmado(){
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeConfirmado.getEstadoPedido().pedidoSaiuParaEntrega()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarSetarPedidoComoEntregue_EmEstadoDeConfirmado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeConfirmado.getEstadoPedido().pedidoEntregue()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarNaoAutorizarPedido_EmEstadoDeConfirmado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeConfirmado.getEstadoPedido().naoAutorizarPedido()
            );
        }
    }

}