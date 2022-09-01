package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.EstadoPedido;

public class ProntoImpl implements EstadoPedido {

    private final Pedido pedido;
    private final Status pronto = Status.PRONTO;

    public ProntoImpl(Pedido pedido) {
        this.pedido = pedido;
        this.pedido.setStatus(pronto);
    }

    @Override
    public void pedidoSaiuParaEntrega() {
        this.pedido.setEstadoPedido(new SaiuParaEntregaImpl(this.pedido));
    }


    @Override
    public void realizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + pronto);
    }

    @Override
    public void pagarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + pronto);
    }

    @Override
    public void confirmarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + pronto);
    }

    @Override
    public void cancelarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + pronto);
    }

    @Override
    public void pedidoPronto() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + pronto);
    }

    @Override
    public void pedidoEntregue() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + pronto);
    }

    @Override
    public void naoAutorizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + pronto);
    }
}
