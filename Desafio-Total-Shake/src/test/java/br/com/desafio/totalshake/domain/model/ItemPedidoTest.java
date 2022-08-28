package br.com.desafio.totalshake.domain.model;

import br.com.desafio.totalshake.exception.QuantidadeInvalidaException;
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

}
