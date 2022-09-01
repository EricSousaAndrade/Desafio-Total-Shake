package br.com.desafio.totalshake.dto;

import br.com.desafio.totalshake.enuns.Status;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class PedidoDto implements Serializable {

    @NotEmpty(message = "O campo status não pode ser vazio")
    private Status status;

    @NotEmpty(message = "O campo itensPedidosList não pode ser vazio.")
    private List<ItemPedidoDto> itensPedidosList;

}
