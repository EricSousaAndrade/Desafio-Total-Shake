package br.com.desafio.totalshake.domain.model;

import br.com.desafio.totalshake.application.errors.exceptions.ItemInexistenteException;
import br.com.desafio.totalshake.builders.PedidoBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    @Test
    public void deve_adicionar_itemNoPedido_ERelacionar_corretamente(){
        var pedido = new Pedido();
        var itemPedido = new ItemPedido();

        pedido.adicionarItem(itemPedido);

        assertAll(
                () -> assertEquals(pedido.getItens().get(0), itemPedido),
                () -> assertEquals(itemPedido.getPedido(), pedido)
        );
    }

    @Test
    public void deve_acrescentar_ItensDoPedido_corretamente(){
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setDescricao("Coca");
        itemPedido.setQuantidade(1);

        pedido.adicionarItem(itemPedido);
        pedido.acrescentarItemDoPedido(1L, 2);

        assertAll(
                () -> assertEquals(3,pedido.getItens().get(0).getQuantidade())
        );
    }

    @Test
    public void deve_reduzir_ItensDoPedido_corretamente(){
        var pedido = PedidoBuilder.umPedido().comUmItemPedido().build();
        var idItemPedido = pedido.getItens().get(0).getId();

        pedido.reduzirItemDoPedido(idItemPedido, 1);

        assertAll(
                () -> assertEquals(1,pedido.getItens().get(0).getQuantidade())
        );
    }

    @Test
    public void deve_remover_ItemDoPedido_quandoQuantidadeRestanteMenorIgualA0_(){
        var pedido = PedidoBuilder.umPedido().comUmItemPedido().build();
        var itemPedido = pedido.getItens().get(0);
        var idItemPedido = itemPedido.getId();

        pedido.reduzirItemDoPedido(idItemPedido, 3);

        assertAll(
                () -> assertFalse(pedido.getItens().contains(itemPedido)),
                () -> assertEquals(0, pedido.getItens().size())
        );
    }

    @Test
    public void deve_lancarExcecaoAoTentarAcrescentar_ItensDoPedido_quandoItemNaoExistente(){
        Pedido pedido = new Pedido();

        assertThrows(ItemInexistenteException.class, () -> pedido.acrescentarItemDoPedido(1L, 20));
    }

    @Test
    public void deve_lancarExcecaoAoTentarReduzir_ItensDoPedido_quandoItemNaoExistente(){
        Pedido pedido = new Pedido();

        assertThrows(ItemInexistenteException.class, () -> pedido.reduzirItemDoPedido(1L, 20));
    }

    @Test
    public void deve_testar_equals_quandoPedidoTiverMesmoID(){
        Pedido pedidoComId1 = PedidoBuilder.umPedido().build();
        Pedido outroPedidoComId1 = PedidoBuilder.umPedido().build();

        assertEquals(pedidoComId1, outroPedidoComId1);
    }

    @Test
    public void deve_testar_equals_casoNull(){
        Pedido pedidoComId1 = PedidoBuilder.umPedido().build();

        assertFalse(pedidoComId1 == null);
    }

}
