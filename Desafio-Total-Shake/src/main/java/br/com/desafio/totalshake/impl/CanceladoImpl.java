package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.EstadoPedido;

public class CanceladoImpl implements EstadoPedido {

    private Pedido pedido;
    private final Status cancelado = Status.CANCELADO;


    public CanceladoImpl(Pedido pedido) {
        this.pedido = pedido;
        this.pedido.setStatus(cancelado);
    }

    @Override
    public void realizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + cancelado);
    }

    @Override
    public void pagarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + cancelado);
    }

    @Override
    public void confirmarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + cancelado);
    }

    @Override
    public void cancelarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + cancelado);
    }

    @Override
    public void pedidoPronto() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + cancelado);
    }

    @Override
    public void pedidoSaiuParaEntrega() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + cancelado);
    }

    @Override
    public void pedidoEntregue() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + cancelado);
    }

    @Override
    public void naoAutorizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + cancelado);
    }
}
