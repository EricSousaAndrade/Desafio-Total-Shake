package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.EstadoPedido;

public class CriadoImpl implements EstadoPedido {

    private final Pedido pedido;
    private final Status criado = Status.CRIADO;

    public CriadoImpl(Pedido pedido) {
        this.pedido = pedido;
        this.pedido.setStatus(criado);
    }

    @Override
    public void realizarPedido() {
        this.pedido.setEstadoPedido(new RealizadoImpl(this.pedido));
    }

    @Override
    public void cancelarPedido() {
        this.pedido.setEstadoPedido(new CanceladoImpl(this.pedido));
    }

    @Override
    public void pagarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + criado);
    }

    @Override
    public void confirmarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + criado);
    }

    @Override
    public void pedidoPronto() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + criado);
    }

    @Override
    public void pedidoSaiuParaEntrega() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + criado);
    }

    @Override
    public void pedidoEntregue() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + criado);
    }

    @Override
    public void naoAutorizarPedido() {
        throw new StatusInvalidoException("Operação inválida, o pedido está em status de: " + criado);
    }
}
