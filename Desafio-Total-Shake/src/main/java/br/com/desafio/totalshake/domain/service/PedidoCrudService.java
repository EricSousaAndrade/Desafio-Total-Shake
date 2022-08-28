package br.com.desafio.totalshake.domain.service;

import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.repository.PedidoRepository;
import br.com.desafio.totalshake.application.exception.PedidoInexistenteException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PedidoCrudService {

    private final PedidoRepository pedidoRepository;

    public PedidoCrudService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public Pedido salvarPedido(Pedido pedido) {
        pedido.setStatus(Status.CONFIRMADO);
        pedido.setDataHora(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido acrescentarItem(Long pedidoId, Long itemId, int quantidade){
        var pedido = this.buscarPedido(pedidoId);
        pedido.acrescentarItemDoPedido(itemId, quantidade);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido reduzirQuantidadeItem(Long pedidoId, Long itemId, int quantidade) {
        var pedido = this.buscarPedido(pedidoId);
        pedido.reduzirItemDoPedido(itemId, quantidade);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido cancelarPedido(Long idPedido) {
        var pedido = this.buscarPedido(idPedido);
        pedido.setStatus(Status.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido buscarPedido(long idPedido) {
        return pedidoRepository
                .findById(idPedido)
                .orElseThrow(() -> new PedidoInexistenteException("Pedido inexistente"));
    }
}
