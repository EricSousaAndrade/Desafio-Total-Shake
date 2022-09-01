package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.EstadoPedido;

public class SaiuParaEntregaImpl implements EstadoPedido {

    private final Pedido pedido;
    private final Status saiuParaEntrega = Status.SAIU_PARA_ENTREGA;

    public SaiuParaEntregaImpl(Pedido pedido) {
        this.pedido = pedido;
        this.pedido.setStatus(saiuParaEntrega);
    }

    @Override
    public void pedidoEntregue() {
        this.pedido.setEstadoPedido(new EntregueImpl(this.pedido));
    }

    @Override
    public void realizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + saiuParaEntrega);
    }

    @Override
    public void pagarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + saiuParaEntrega);
    }

    @Override
    public void confirmarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + saiuParaEntrega);
    }

    @Override
    public void cancelarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + saiuParaEntrega);
    }

    @Override
    public void pedidoPronto() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + saiuParaEntrega);
    }

    @Override
    public void pedidoSaiuParaEntrega() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + saiuParaEntrega);
    }

    @Override
    public void naoAutorizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + saiuParaEntrega);
    }
}
