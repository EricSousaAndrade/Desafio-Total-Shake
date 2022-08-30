package br.com.desafio.totalshake.application.controller.request;

import br.com.desafio.totalshake.domain.model.ItemPedido;

import javax.validation.constraints.*;

public class ItemPedidoDTO {

    @NotNull @NotBlank
    @Size(min = 3, max = 120)
    private String descricao;

    @Positive @NotNull
    private Integer quantidade;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
