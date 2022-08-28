package br.com.desafio.totalshake.domain.model;

import br.com.desafio.totalshake.application.exception.ItemInexistenteException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    @Test
    public void deve_adicionar_itemNoPedido_ERelacionar_corretamente(){
        var pedido = new Pedido();
        var itemPedido = new ItemPedido();

        pedido.adicionarItem(itemPedido);

        assertAll(
                () -> assertEquals(pedido.getItensPedido().get(0), itemPedido),
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
                () -> assertEquals(3,pedido.getItensPedido().get(0).getQuantidade())
        );
    }

    @Test
    public void deve_reduzir_ItensDoPedido_corretamente(){
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setDescricao("Coca");
        itemPedido.setQuantidade(2);

        pedido.adicionarItem(itemPedido);
        pedido.reduzirItemDoPedido(1L, 1);

        assertAll(
                () -> assertEquals(1,pedido.getItensPedido().get(0).getQuantidade())
        );
    }

    @Test
    public void deve_remover_ItemDoPedido_quandoQuantidadeRestanteMenorIgualA0_(){
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setDescricao("Coca");
        itemPedido.setQuantidade(2);

        pedido.adicionarItem(itemPedido);
        pedido.reduzirItemDoPedido(1L, 3);

        assertAll(
                () -> assertFalse(pedido.getItensPedido().contains(itemPedido)),
                () -> assertEquals(0, pedido.getItensPedido().size())
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

}
