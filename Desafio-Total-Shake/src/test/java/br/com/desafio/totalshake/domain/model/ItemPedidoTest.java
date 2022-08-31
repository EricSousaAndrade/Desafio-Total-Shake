package br.com.desafio.totalshake.domain.model;

import br.com.desafio.totalshake.application.errors.exceptions.QuantidadeInvalidaException;
import br.com.desafio.totalshake.builders.PedidoBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemPedidoTest {

    @Test
    public void deve_diminuirQuantidadeDe_itemPedido_corretamente(){
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setDescricao("Café espresso");
        itemPedido.setQuantidade(2);

        int quantidadeAtual = itemPedido.reduzirQuantidadeItem(1);

        assertEquals(1, quantidadeAtual);
    }

    @Test
    public void deve_aumentarQuantidadeDe_itemPedido_corretamente(){
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setDescricao("Café espresso");
        itemPedido.setQuantidade(2);

        int quantidadeAtual = itemPedido.acrescentarQuantidadeItem(3);

        assertEquals(5, quantidadeAtual);
    }

    @Test
    public void deve_lancarExcecaoAoTentarAcrescentar_qtdItem_quandoMenorOuIgualQue0(){
        ItemPedido itemPedido = new ItemPedido();

        assertAll(
                () -> assertThrows(
                        QuantidadeInvalidaException.class,
                        () -> itemPedido.acrescentarQuantidadeItem(0)
                ),
                () -> assertThrows(
                        QuantidadeInvalidaException.class,
                        () -> itemPedido.acrescentarQuantidadeItem(-3)
                )
        );
    }

    @Test
    public void deve_lancarExcecaoAoTentarReduzir_qtdItem_quandoMenorOuIgualQue0(){
        ItemPedido itemPedido = new ItemPedido();

        assertAll(
                () -> assertThrows(
                        QuantidadeInvalidaException.class,
                        () -> itemPedido.reduzirQuantidadeItem(0)
                ),
                () -> assertThrows(
                        QuantidadeInvalidaException.class,
                        () -> itemPedido.reduzirQuantidadeItem(-3)
                )
        );
    }

    @Test
    public void deve_testar_equals_quandoItemPedidoTiver_mesmaDescricao_estarNoMesmoPedido(){
        Pedido pedido = PedidoBuilder.umPedido().build();
        ItemPedido itemPedido = new ItemPedido("Coca",2);
        itemPedido.setPedido(pedido);
        ItemPedido mesmoItemPedido = new ItemPedido("Coca",3);
        mesmoItemPedido.setPedido(pedido);

        assertEquals(itemPedido, mesmoItemPedido);
    }

    @Test
    public void deve_testar_equals_casoNull(){
        ItemPedido itemPedido = new ItemPedido("Coca",2);

        assertFalse(itemPedido == null);
    }

}
