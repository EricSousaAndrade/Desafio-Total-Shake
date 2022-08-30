package br.com.desafio.totalshake.dtos;

import br.com.desafio.totalshake.entities.ItemPedido;
import br.com.desafio.totalshake.entities.Pedido;

public class ItemPedidoDto {

    private Long id;
    private Integer quantidade;
    private String descricao;
    private Pedido pedido;

    public ItemPedidoDto() {
    }

    public ItemPedidoDto(Long id, Integer quantidade, String descricao, Pedido pedido) {
        this.id = id;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.pedido = pedido;
    }

    public ItemPedidoDto(ItemPedido entity) {
        this.id = entity.getId();
        this.quantidade = entity.getQuantidade();
        this.descricao = entity.getDescricao();
        this.pedido = entity.getPedido();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
