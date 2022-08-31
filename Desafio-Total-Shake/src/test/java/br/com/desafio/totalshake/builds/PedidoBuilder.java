package br.com.desafio.totalshake.builds;

import br.com.desafio.totalshake.domain.model.ItemPedido;
import br.com.desafio.totalshake.domain.model.Pedido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PedidoBuilder {

    private Pedido pedido;

    private PedidoBuilder(){

    }

    public static PedidoBuilder umPedido(){
        var pedidoBuilder = new PedidoBuilder();
        pedidoBuilder.pedido = new Pedido();
        pedidoBuilder.pedido.setId(1L);

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
