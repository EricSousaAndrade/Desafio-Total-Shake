package br.com.desafio.totalshake.domain.model;

import br.com.desafio.totalshake.application.errors.exceptions.ItemInexistenteException;
import br.com.desafio.totalshake.application.errors.CodInternoErroApi;
import br.com.desafio.totalshake.domain.service.EstadoPedido;
import br.com.desafio.totalshake.impl.EstadoPedidoFactory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "pedido"
    )
    private DataHoraStatusPedido dataHoraStatus;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CRIADO;

    @Transient
    private  EstadoPedido estadoPedido;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ItemPedido> itens;

    public void adicionarItem(ItemPedido itemPedido){
        this.garantirNullSafetyItens();
        itemPedido.setPedido(this);
        itens.add(itemPedido);
    }

    public void acrescentarItemDoPedido(long idItemPedido, int quantidade) {
        this.garantirNullSafetyItens();
        this.itens.stream()
                .filter(itemPedido -> itemPedido.getId() == idItemPedido)
                .findFirst()
                .ifPresentOrElse(
                        itemPedido -> itemPedido.acrescentarQuantidadeItem(quantidade),
                        () -> {
                            throw new ItemInexistenteException(
                                    CodInternoErroApi.AP202.getCodigo(),
                                    CodInternoErroApi.AP202.getMensagem()
                            );
                        }
                );
    }

    public void reduzirItemDoPedido(long idItemPedido, int quantidade) {
        this.garantirNullSafetyItens();
        this.itens.stream()
                .filter(itemPedido -> itemPedido.getId() == idItemPedido)
                .findFirst()
                .ifPresentOrElse(
                        itemPedido -> {
                            int qtdAtual = itemPedido.reduzirQuantidadeItem(quantidade);
                            if(qtdAtual <= 0){ this.itens.remove(itemPedido); }
                        },
                        () -> {
                            throw new ItemInexistenteException(
                                    CodInternoErroApi.AP202.getCodigo(),
                                    CodInternoErroApi.AP202.getMensagem()
                            );
                        }
                );
    }

    public void criarPedido() {
        this.garantirNullSafetyEstadoPedido();
        this.garantirNullSafetyDataHoraStatus();
        this.dataHoraStatus.salvarDataHoraCriacao();
    }

    public void realizarPedido(){
        this.garantirNullSafetyEstadoPedido();
        this.estadoPedido.realizarPedido();
        this.garantirNullSafetyDataHoraStatus();
        this.dataHoraStatus.salvarDataHoraRealizado();
    }

    public void cancelarPedido(){
        this.garantirNullSafetyEstadoPedido();
        this.estadoPedido.cancelarPedido();
        this.garantirNullSafetyDataHoraStatus();
        this.dataHoraStatus.salvarDataHoraCancelado();
    }

    private void garantirNullSafetyItens() {
        if(itens == null){
            itens = new ArrayList<>();
        }
    }

    private void garantirNullSafetyEstadoPedido() {
        if(this.estadoPedido == null){
            this.estadoPedido = EstadoPedidoFactory.ofStatus(this.status, this);
        }
    }

    private void garantirNullSafetyDataHoraStatus() {
        if(this.dataHoraStatus == null){
            this.dataHoraStatus = new DataHoraStatusPedido();
            this.dataHoraStatus.setPedido(this);
        }
    }

    @PrePersist
    public void setarUltimaAtualizacaoPedido(){
        this.dataHora = LocalDateTime.now();
    }

    public DataHoraStatusPedido getDataHoraStatus() {
        if(this.dataHoraStatus == null){
            this.dataHoraStatus = new DataHoraStatusPedido();
        }
        return dataHoraStatus;
    }

    public EstadoPedido getEstadoPedido() {
        this.garantirNullSafetyEstadoPedido();
        return estadoPedido;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
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
