package br.com.desafio.totalshake.domain.model;

import br.com.desafio.totalshake.application.errors.exceptions.QuantidadeInvalidaException;
import br.com.desafio.totalshake.application.errors.CodInternoErroApi;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public ItemPedido(String descricao, int quantidade) {
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public ItemPedido(){}

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int reduzirQuantidadeItem(int quantidadeParaReduzir) {
        this.validarQuantidade(quantidadeParaReduzir);
        return this.quantidade -= quantidadeParaReduzir;
    }

    public int acrescentarQuantidadeItem(int quantidadeParaAcrescentar) {
        this.validarQuantidade(quantidadeParaAcrescentar);
        return this.quantidade += quantidadeParaAcrescentar;
    }

    private void validarQuantidade(int quantidade) {
        if(quantidade <= 0){
            throw new QuantidadeInvalidaException(
                    CodInternoErroApi.AP004.getCodigo(),
                    CodInternoErroApi.AP004.getMensagem()
            );
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id) && Objects.equals(descricao, that.descricao) && Objects.equals(pedido, that.pedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, pedido);
    }
}
