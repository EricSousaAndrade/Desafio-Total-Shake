package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanceladoImplTest {

    Pedido pedidoEmEstadoDeCancelado;

    @BeforeEach
    public void setUp(){
        pedidoEmEstadoDeCancelado = new Pedido();
        pedidoEmEstadoDeCancelado.setStatus(Status.CANCELADO);
        pedidoEmEstadoDeCancelado.setEstadoPedido(new CanceladoImpl(pedidoEmEstadoDeCancelado));
    }

    @Nested
    class TestesExcecaoDeAlteracaoImpossivelDeEstado{

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarPagarPedidoCancelado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeCancelado.getEstadoPedido().pagarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarSetarEntregueEmPedidoCancelado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeCancelado.getEstadoPedido().pedidoEntregue()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_setarPedidoCancelado_ParaEstadoParaEntregue() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeCancelado.getEstadoPedido().pedidoSaiuParaEntrega()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarCancelarPedidoJaCancelado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeCancelado.getEstadoPedido().cancelarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_tentarRealizarPedidoCancelado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeCancelado.getEstadoPedido().realizarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarConfirmarPedidoCancelado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeCancelado.getEstadoPedido().confirmarPedido()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarSetarPedidoQueSaiuParaEntrega_emPedidoCancelado(){
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeCancelado.getEstadoPedido().pedidoPronto()
            );
        }

        @Test
        void deve_lancarExcecaoDeStatusAo_TentarNaoAutorizarPedido_emPedidoCancelado() {
            assertThrows(
                    StatusInvalidoException.class,
                    () -> pedidoEmEstadoDeCancelado.getEstadoPedido().naoAutorizarPedido()
            );
        }
    }

}