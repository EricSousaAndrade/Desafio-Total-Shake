package br.com.desafio.totalshake.impl;

import br.com.desafio.totalshake.application.errors.CodInternoErroApi;
import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.EstadoPedido;

public class NaoAutorizadoImpl implements EstadoPedido {

    private Pedido pedido;
    private final Status naoAutorizado = Status.NAO_AUTORIZADO;

    public NaoAutorizadoImpl(Pedido pedido) {
        this.pedido = pedido;
        this.pedido.setStatus(naoAutorizado);
    }

    @Override
    public void realizarPedido() {
        throw new StatusInvalidoException(
                CodInternoErroApi.AP301.getMensagem(),
                CodInternoErroApi.AP301.getCodigo(),
                naoAutorizado
        );
    }

    @Override
    public void pagarPedido() {
        throw new StatusInvalidoException(
                CodInternoErroApi.AP301.getMensagem(),
                CodInternoErroApi.AP301.getCodigo(),
                naoAutorizado
        );
    }

    @Override
    public void confirmarPedido() {
        throw new StatusInvalidoException(
                CodInternoErroApi.AP301.getMensagem(),
                CodInternoErroApi.AP301.getCodigo(),
                naoAutorizado
        );
    }

    @Override
    public void cancelarPedido() {
        throw new StatusInvalidoException(
                CodInternoErroApi.AP301.getMensagem(),
                CodInternoErroApi.AP301.getCodigo(),
                naoAutorizado
        );
    }

    @Override
    public void pedidoPronto() {
        throw new StatusInvalidoException(
                CodInternoErroApi.AP301.getMensagem(),
                CodInternoErroApi.AP301.getCodigo(),
                naoAutorizado
        );
    }

    @Override
    public void pedidoSaiuParaEntrega() {
        throw new StatusInvalidoException(
                CodInternoErroApi.AP301.getMensagem(),
                CodInternoErroApi.AP301.getCodigo(),
                naoAutorizado
        );
    }

    @Override
    public void pedidoEntregue() {
        throw new StatusInvalidoException(
                CodInternoErroApi.AP301.getMensagem(),
                CodInternoErroApi.AP301.getCodigo(),
                naoAutorizado
        );
    }

    @Override
    public void naoAutorizarPedido() {
        throw new StatusInvalidoException(
                CodInternoErroApi.AP301.getMensagem(),
                CodInternoErroApi.AP301.getCodigo(),
                naoAutorizado
        );
    }
}
