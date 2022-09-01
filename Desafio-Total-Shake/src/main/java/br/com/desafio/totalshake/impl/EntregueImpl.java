package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.EstadoPedido;

public class EntregueImpl implements EstadoPedido {

    private Pedido pedido;
    private final Status entregue = Status.ENTREGUE;

    public EntregueImpl(Pedido pedido) {
        this.pedido = pedido;
        this.pedido.setStatus(entregue);
    }

    @Override
    public void realizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + entregue);
    }

    @Override
    public void pagarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + entregue);
    }

    @Override
    public void confirmarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + entregue);
    }

    @Override
    public void cancelarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + entregue);
    }

    @Override
    public void pedidoPronto() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + entregue);
    }

    @Override
    public void pedidoSaiuParaEntrega() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + entregue);
    }

    @Override
    public void pedidoEntregue() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + entregue);
    }

    @Override
    public void naoAutorizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + entregue);
    }
}
