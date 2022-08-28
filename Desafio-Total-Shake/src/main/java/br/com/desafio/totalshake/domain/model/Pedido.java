package br.com.desafio.totalshake.domain.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private Status status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itensPedido;

    public void adicionarItem(ItemPedido itemPedido){
        if(itemPedido != null){
            this.garantirNullSafetyItensPedido();
            itemPedido.setPedido(this);
            itensPedido.add(itemPedido);
        }
    }

    public void acrescentarItemDoPedido(long idPedido, int quantidade) {
        this.garantirNullSafetyItensPedido();
        this.itensPedido.stream()
                .filter(itemPedido -> itemPedido.getId() == idPedido)
                .findFirst()
                .ifPresentOrElse(
                        itemPedido -> itemPedido.acrescentarQuantidadeItem(quantidade),
                        () -> { throw new ItemInexistenteException("Esse item não existe no pedido"); }
                );
    }

    public void reduzirItemDoPedido(long idPedido, int quantidade) {
        this.garantirNullSafetyItensPedido();
        this.itensPedido.stream()
                .filter(itemPedido -> itemPedido.getId() == idPedido)
                .findFirst()
                .ifPresentOrElse(
                        itemPedido -> {
                            int qtdAtual = itemPedido.reduzirQuantidadeItem(quantidade);
                            if(qtdAtual <= 0){
                                this.itensPedido.remove(itemPedido);
                            }
                        },
                        () -> { throw new ItemInexistenteException("Esse item não existe no pedido"); }
                );
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    private void garantirNullSafetyItensPedido() {
        if(itensPedido == null){
            itensPedido = new ArrayList<>();
        }
    }
}
