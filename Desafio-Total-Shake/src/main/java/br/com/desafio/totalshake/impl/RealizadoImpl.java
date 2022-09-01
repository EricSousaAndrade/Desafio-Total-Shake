package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.EstadoPedido;

public class RealizadoImpl implements EstadoPedido {

    private final Pedido pedido;
    private final Status realizado = Status.REALIZADO;

    public RealizadoImpl(Pedido pedido) {
        this.pedido = pedido;
        this.pedido.setStatus(realizado);
    }

    @Override
    public void pagarPedido() {
        this.pedido.setEstadoPedido(new PagoImpl(this.pedido));
    }

    @Override
    public void cancelarPedido() {
        this.pedido.setEstadoPedido(new CanceladoImpl(this.pedido));
    }

    @Override
    public void confirmarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + realizado);
    }

    @Override
    public void realizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + realizado);
    }

    @Override
    public void pedidoPronto() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + realizado);
    }

    @Override
    public void pedidoSaiuParaEntrega() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + realizado);
    }

    @Override
    public void pedidoEntregue() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + realizado);
    }

    @Override
    public void naoAutorizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + realizado);
    }
}
