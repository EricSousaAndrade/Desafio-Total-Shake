package br.com.desafio.totalshake.domain.service;

import br.com.desafio.totalshake.application.exception.PedidoInexistenteException;
import br.com.desafio.totalshake.domain.model.ItemPedido;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.repository.PedidoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class PedidoCrudServiceTest {

    @MockBean
    private PedidoRepository pedidoRepository;

    @Autowired
    PedidoCrudService pedidoService;

    @Test
    public void deve_criarUmPedidoComItens_corretamente(){
        Pedido pedido = new Pedido();
        List<ItemPedido> itensPedido = new ArrayList<>(Arrays.asList(
               new ItemPedido("Coca cola", 2),
               new ItemPedido("Arroz frito",1)
        ));
        pedido.setItensPedido(itensPedido);

        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        var pedidoSalvo = pedidoService.salvarPedido(pedido);

        assertThat(pedidoSalvo.getItensPedido().size()).isEqualTo(2);
        assertThat(pedidoSalvo.getStatus()).isEqualTo(Status.CONFIRMADO);
        assertThat(pedidoSalvo.getDataHora()).isNotNull();
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void deve_buscarUmPedido_corretamente(){
        Pedido pedido = new Pedido();
        List<ItemPedido> itensPedido = new ArrayList<>(Arrays.asList(
                new ItemPedido("Coca cola", 2),
                new ItemPedido("Arroz frito",1)
        ));
        pedido.setId(1L);
        pedido.setItensPedido(itensPedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        var pedidoEncontrado = pedidoService.buscarPedido(1L);

        assertThat(pedidoEncontrado.getItensPedido().size()).isEqualTo(2);
        assertThat(pedidoEncontrado.getId()).isEqualTo(1L);
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    public void deve_lancarExcecaoAoBuscarUmPedido_quandoInexistente(){

        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                PedidoInexistenteException.class,
                () -> pedidoService.buscarPedido(1L)
        );

        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    public void deve_incrementarAQuantidadeDeUm_itemPedido_corretamente(){
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido("Coca", 2);
        itemPedido.setId(2L);
        pedido.setId(1L);
        pedido.adicionarItem(itemPedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        var pedidoAcrescentado = pedidoService.acrescentarItem(1L, 2L, 3);
        var itemPedidoAcrescentado = pedidoAcrescentado.getItensPedido().get(0);

        assertThat(itemPedidoAcrescentado.getQuantidade()).isEqualTo(5);
    }

    @Test
    public void deve_reduzirAQuantidadeDeUm_itemPedido_corretamente(){
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido("Coca", 2);
        itemPedido.setId(2L);
        pedido.setId(1L);
        pedido.adicionarItem(itemPedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        var pedidoReduzido = pedidoService.reduzirQuantidadeItem(1L, 2L, 1);
        var itemPedidoReduzido = pedidoReduzido.getItensPedido().get(0);

        assertThat(itemPedidoReduzido.getQuantidade()).isEqualTo(1);
    }

    @Test
    public void deve_removerUm_itemPedido_quandoQtdRestanteForMenorIgual0(){
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido("Coca", 2);
        itemPedido.setId(2L);
        pedido.setId(1L);
        pedido.adicionarItem(itemPedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        var pedidoSemItens = pedidoService.reduzirQuantidadeItem(1L, 2L, 2);

        assertThat(pedidoSemItens.getItensPedido().size()).isEqualTo(0);
    }

    @Test
    public void deve_cancelarUm_pedido_corretamente(){
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido("Coca", 2);
        itemPedido.setId(2L);
        pedido.setId(1L);
        pedido.adicionarItem(itemPedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        var pedidoCancelado = pedidoService.cancelarPedido(1L);

        assertThat(pedidoCancelado.getStatus()).isEqualTo(Status.CANCELADO);
    }

}
