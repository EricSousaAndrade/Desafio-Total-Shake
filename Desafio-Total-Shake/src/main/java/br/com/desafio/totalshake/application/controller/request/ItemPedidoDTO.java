package br.com.desafio.totalshake.application.controller.request;

import br.com.desafio.totalshake.domain.model.ItemPedido;

import javax.validation.constraints.*;

public record ItemPedidoDTO(
        @NotNull @NotBlank @Size(min = 3, max = 120)
        String descricao,
        @Positive @NotNull
        Integer quantidade
) {

    public ItemPedidoDTO(String descricao, Integer quantidade) {
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public ItemPedido toItemPedidoModel() {
        var itemPedido = new ItemPedido();
        itemPedido.setDescricao(this.descricao);
        itemPedido.setQuantidade(this.quantidade);

        return itemPedido;
    }

    @Override
    public String descricao() {
        return descricao;
    }

    @Override
    public Integer quantidade() {
        return quantidade;
    }
}
