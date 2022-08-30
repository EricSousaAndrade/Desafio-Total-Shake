package br.com.desafio.totalshake.dtos;

import br.com.desafio.totalshake.entities.ItemPedido;
import br.com.desafio.totalshake.entities.Pedido;
import br.com.desafio.totalshake.entities.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHora;
    private Status status;
    private List<ItemPedidoDto> itensDoPedido;

    public PedidoDto() {
    }

    public PedidoDto(Long id, LocalDateTime dataHora, Status status) {
        this.id = id;
        this.dataHora = dataHora;
        this.status = status;
    }

    public PedidoDto(Pedido entity) {
        super();
        this.id = entity.getId();
        this.dataHora = entity.getDataHora();
        this.status = entity.getStatus();
    }

    public PedidoDto(Pedido entity, List<ItemPedido> itensDoPedido) {
        this(entity);
        itensDoPedido.forEach(itemDoPedido -> this.itensDoPedido.add(new ItemPedidoDto(itemDoPedido)));
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

    public List<ItemPedidoDto> getItensDoPedido() {
        return itensDoPedido;
    }

    public void setItensDoPedido(List<ItemPedidoDto> itensDoPedido) {
        this.itensDoPedido = itensDoPedido;
    }
}
