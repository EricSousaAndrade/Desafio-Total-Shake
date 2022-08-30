package br.com.desafio.totalshake.dto;

import br.com.desafio.totalshake.enuns.Status;
import br.com.desafio.totalshake.model.ItemPedido;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoDto implements Serializable {

    private LocalDateTime dataHora = LocalDateTime.now();

    @NotEmpty(message = "O campo status não pode ser vazio")
    private Status status;

    @NotEmpty(message = "O campo itensPedidosList não pode ser vazio.")
    private List<ItemPedido> itensPedidosList;
}
