package br.com.desafio.totalshake.domain.model;

import br.com.desafio.totalshake.application.exception.ItemInexistenteException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pedido")
    private List<ItemPedido> itens;

    public void adicionarItem(ItemPedido itemPedido){
        this.garantirNullSafetyItensPedido();
        itemPedido.setPedido(this);
        itens.add(itemPedido);
    }

    public void acrescentarItemDoPedido(long idPedido, int quantidade) {
        this.garantirNullSafetyItensPedido();
        this.itens.stream()
                .filter(itemPedido -> itemPedido.getId() == idPedido)
                .findFirst()
                .ifPresentOrElse(
                        itemPedido -> itemPedido.acrescentarQuantidadeItem(quantidade),
                        () -> { throw new ItemInexistenteException("Esse item não existe no pedido"); }
                );
    }

    public void reduzirItemDoPedido(long idPedido, int quantidade) {
        this.garantirNullSafetyItensPedido();
        this.itens.stream()
                .filter(itemPedido -> itemPedido.getId() == idPedido)
                .findFirst()
                .ifPresentOrElse(
                        itemPedido -> {
                            int qtdAtual = itemPedido.reduzirQuantidadeItem(quantidade);
                            if(qtdAtual <= 0){
                                this.itens.remove(itemPedido);
                            }
                        },
                        () -> { throw new ItemInexistenteException("Esse item não existe no pedido"); }
                );
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void garantirNullSafetyItensPedido() {
        if(itens == null){
            itens = new ArrayList<>();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
