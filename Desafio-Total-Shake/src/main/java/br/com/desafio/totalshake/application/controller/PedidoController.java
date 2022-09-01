package br.com.desafio.totalshake.application.controller;

import br.com.desafio.totalshake.application.controller.request.ItemPedidoDTO;
import br.com.desafio.totalshake.application.controller.request.PedidoDTOPost;
import br.com.desafio.totalshake.application.controller.response.PedidoDTOResponse;
import br.com.desafio.totalshake.domain.service.crud.PedidoCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoCrudService pedidoCrudService;

    public PedidoController(PedidoCrudService pedidoCrudService) {
        this.pedidoCrudService = pedidoCrudService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTOResponse> criarPedido(@RequestBody @Valid PedidoDTOPost pedidoPostDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoCrudService.criarPedido(pedidoPostDTO));
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTOResponse> buscarPedido(@PathVariable Long idPedido){
        return ResponseEntity.ok(new PedidoDTOResponse(pedidoCrudService.buscarPedidoPorId(idPedido)));
    }

    @PostMapping("/{idPedido}/adicionar-item")
    public ResponseEntity<PedidoDTOResponse> adicionarItem(@PathVariable Long idPedido,
                                                           @RequestBody @Valid ItemPedidoDTO itemPedidoDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoCrudService.adicionarItemNoPedido(idPedido, itemPedidoDTO));
    }

    @PutMapping("/{idPedido}/realizar")
    public ResponseEntity<PedidoDTOResponse> realizarPedido(@PathVariable Long idPedido){
        return ResponseEntity.ok(pedidoCrudService.realizarPedido(idPedido));
    }

    @PutMapping("/{idPedido}/cancelar")
    public ResponseEntity<PedidoDTOResponse> cancelarPedido(@PathVariable Long idPedido){
        return ResponseEntity.ok(pedidoCrudService.cancelarPedido(idPedido));
    }

    @PutMapping("/{idPedido}/itens/{itemId}/acrescentar/{quantidade}")
    public ResponseEntity<PedidoDTOResponse> acrescentarItem(@PathVariable Long idPedido, @PathVariable Long itemId,
                                                             @PathVariable Integer quantidade){
        return ResponseEntity.ok(pedidoCrudService.acrescentarItem(idPedido, itemId, quantidade));
    }

    @PutMapping("/{idPedido}/itens/{itemId}/reduzir/{quantidade}")
    public ResponseEntity<PedidoDTOResponse> reduzirItem(@PathVariable Long idPedido, @PathVariable Long itemId,
                                                             @PathVariable Integer quantidade){
        return ResponseEntity.ok(pedidoCrudService.reduzirQuantidadeItem(idPedido, itemId, quantidade));
    }

}
