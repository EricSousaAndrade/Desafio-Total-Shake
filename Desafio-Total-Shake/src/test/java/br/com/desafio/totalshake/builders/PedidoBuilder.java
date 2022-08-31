package br.com.desafio.totalshake.builders;

import br.com.desafio.totalshake.domain.model.ItemPedido;
import br.com.desafio.totalshake.domain.model.Pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {

    private Pedido pedido;

    private PedidoBuilder(){

    }

    public static PedidoBuilder umPedido(){
        var pedidoBuilder = new PedidoBuilder();
        pedidoBuilder.pedido = new Pedido();
        pedidoBuilder.pedido.setId(1L);
        pedidoBuilder.pedido.setDataHora(LocalDateTime.now());

        return pedidoBuilder;
    }

    public PedidoBuilder comUmItemPedido() {
        var itemPedido =  new ItemPedido("Coca cola", 2);
        itemPedido.setId(1L);
        pedido.setItens(new ArrayList<>(List.of(itemPedido)));
        return this;
    }

    public Pedido build(){
        return this.pedido;
    }


}
