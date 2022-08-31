package br.com.desafio.totalshake.application.controller.response;

import br.com.desafio.totalshake.domain.model.DataHoraStatusPedido;
import br.com.desafio.totalshake.domain.model.ItemPedido;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

import java.util.List;

public class PedidoDTOResponse {

    private final Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime dataHora;

    private final Status status;

    @JsonIgnoreProperties("pedido")
    private final List<ItemPedido> itens;

    @JsonIgnoreProperties(value = {"pedido", "id"})
    @JsonProperty("dataHoraStatus")
    private final DataHoraStatusPedido dataHoraStatus;

    public PedidoDTOResponse(Pedido pedidoCriado) {
        this.id = pedidoCriado.getId();
        this.dataHora = pedidoCriado.getDataHora();
        this.itens = pedidoCriado.getItens();
        this.status = pedidoCriado.getStatus();
        this.dataHoraStatus = pedidoCriado.getDataHoraStatus();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Status getStatus() {
        return status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public DataHoraStatusPedido getDataHoraStatusPedido() {
        return dataHoraStatus;
    }
}
