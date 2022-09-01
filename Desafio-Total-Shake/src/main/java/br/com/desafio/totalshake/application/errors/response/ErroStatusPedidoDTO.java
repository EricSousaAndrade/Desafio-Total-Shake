package br.com.desafio.totalshake.application.errors.response;

import br.com.desafio.totalshake.domain.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErroStatusPedidoDTO  {

    @JsonProperty("statusPedido")
    private final Status statusAtualDoPedido;

    public ErroStatusPedidoDTO(Status statusAtualDoPedido) {
        this.statusAtualDoPedido = statusAtualDoPedido;
    }

    public Status getStatusAtualDoPedido() {
        return statusAtualDoPedido;
    }
}
