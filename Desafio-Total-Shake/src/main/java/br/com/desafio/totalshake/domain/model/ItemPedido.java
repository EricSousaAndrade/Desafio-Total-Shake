package br.com.desafio.totalshake.domain.model;

import br.com.desafio.totalshake.exception.QuantidadeInvalidaException;

import javax.persistence.*;

@Entity
@Table(name = "ITEM_PEDIDO")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    private void validarQuantidade(int quantidadeParaReduzir) {
        if(quantidadeParaReduzir <= 0){
            throw new QuantidadeInvalidaException("Quantidade inválida, informe um valor maior que 0");
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
}
