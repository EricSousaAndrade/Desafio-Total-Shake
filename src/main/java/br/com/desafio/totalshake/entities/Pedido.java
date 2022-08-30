package br.com.desafio.totalshake.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pedido")
    private List<ItemPedido> itensDoPedido;

    public Pedido() {
    }

    public Pedido(Long id, LocalDateTime dataHora, Status status, List<ItemPedido> itensDoPedido) {
        this.id = id;
        this.dataHora = dataHora;
        this.status = status;
        this.itensDoPedido = itensDoPedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ItemPedido> getItensDoPedido() {
        return itensDoPedido;
    }

    public void setItensDoPedido(List<ItemPedido> itensDoPedido) {
        this.itensDoPedido = itensDoPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pedido pedido = (Pedido) o;

        return id.equals(pedido.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
