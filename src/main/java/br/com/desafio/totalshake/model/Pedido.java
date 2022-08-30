package br.com.desafio.totalshake.model;

import br.com.desafio.totalshake.enuns.Status;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    private List<ItemPedido> itensPedidosList;

}
