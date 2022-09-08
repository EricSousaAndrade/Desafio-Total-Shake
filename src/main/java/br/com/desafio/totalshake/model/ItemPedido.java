package br.com.desafio.totalshake.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Integer quantidade;

    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Pedido pedido;

}