package br.com.desafio.totalshake.application.controller.request;

import br.com.desafio.totalshake.domain.model.ItemPedido;
import br.com.desafio.totalshake.domain.model.Pedido;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PedidoDTOPost {

    @NotNull @NotEmpty
    List<@Valid ItemPedidoDTO> itens;

    public Pedido toPedidoModel() {
        var pedido = new Pedido();
        List<ItemPedido> itensPedido = itens
                .stream()
                .map(ItemPedidoDTO::toItemPedidoModel)
                .toList();

        itensPedido.forEach(pedido::adicionarItem);

        return pedido;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
