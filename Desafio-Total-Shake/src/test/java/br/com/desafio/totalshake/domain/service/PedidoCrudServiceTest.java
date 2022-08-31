package br.com.desafio.totalshake.domain.service;

import br.com.desafio.totalshake.application.controller.request.ItemPedidoDTO;
import br.com.desafio.totalshake.application.controller.request.PedidoDTOPost;
import br.com.desafio.totalshake.application.errors.exceptions.PedidoInexistenteException;
import br.com.desafio.totalshake.domain.model.ItemPedido;
import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.repository.PedidoRepository;
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
import static org.junit.jupiter.api.Assertions.*;
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
        PedidoDTOPost pedidoDTOPost = new PedidoDTOPost();
        List<ItemPedidoDTO> itensPedidoDto = new ArrayList<>(Arrays.asList(
               new ItemPedidoDTO("Coca cola", 2),
               new ItemPedidoDTO("Arroz frito",1)
        ));
        pedidoDTOPost.setItens(itensPedidoDto);

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoDTOPost.toPedidoModel());

        var pedidoSalvo = pedidoService.salvarPedido(pedidoDTOPost);

        assertThat(pedidoSalvo.getItens().size()).isEqualTo(2);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void deve_adicionarUmItemNoPedido_corretamente(){

        ItemPedidoDTO itemPedidoDto = new ItemPedidoDTO("feijao",2);
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        var pedidoSalvo = pedidoService.adicionarItemNoPedido(1L, itemPedidoDto);
        var itemPedido = pedido.getItens().get(0);

        assertThat(pedidoSalvo.getItens().size()).isEqualTo(1);
        assertTrue(pedidoSalvo.getItens().contains(itemPedido));
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void deve_realizarOPedido_corretamente(){

        Pedido pedido = new Pedido();
        pedido.setId(1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        var pedidoSalvo = pedidoService.realizarPedido(1L);

        assertEquals(Status.REALIZADO, pedidoSalvo.getStatus());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void deve_buscarUmPedido_corretamente(){
        Pedido pedido = new Pedido();
        List<ItemPedido> itensPedido = new ArrayList<>(Arrays.asList(
                new ItemPedido("Coca cola", 2),
                new ItemPedido("Arroz frito",1)
        ));
        pedido.setId(1L);
        pedido.setItens(itensPedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        var pedidoEncontrado = pedidoService.buscarPedidoPorId(1L);

        assertThat(pedidoEncontrado.getItens().size()).isEqualTo(2);
        assertThat(pedidoEncontrado.getId()).isEqualTo(1L);
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    public void deve_lancarExcecaoAoBuscarUmPedido_quandoInexistente(){

        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                PedidoInexistenteException.class,
                () -> pedidoService.buscarPedidoPorId(1L)
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
        var itemPedidoAcrescentado = pedidoAcrescentado.getItens().get(0);

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
        var itemPedidoReduzido = pedidoReduzido.getItens().get(0);

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

        assertThat(pedidoSemItens.getItens().size()).isEqualTo(0);
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
