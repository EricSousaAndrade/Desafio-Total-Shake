package br.com.desafio.totalshake.controller;

import br.com.desafio.totalshake.dto.ItemPedidoDto;
import br.com.desafio.totalshake.exception.NaoEncontradoException;
import br.com.desafio.totalshake.exception.ParametroInvalidoException;
import br.com.desafio.totalshake.model.ItemPedido;
import br.com.desafio.totalshake.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itempedido")
public class ItemPedidoController {

    @Autowired
    ItemPedidoService service;

//    @PostMapping()
//    public ResponseEntity<ItemPedido> postItemPedido(@RequestBody ItemPedidoDto itemPedidoDto) throws Exception {
//
//        try{
//            service.salvarItemPedido(itemPedidoDto);
//            return ResponseEntity.ok().build();
//
//        } catch (ParametroInvalidoException e){
//
//            return ResponseEntity.badRequest().build();
//        }
//
//    }
//
//    @PutMapping("/{idItemPedido}")
//    public ResponseEntity putItemPedido(@PathVariable Long idItemPedido, @RequestBody ItemPedidoDto itemPedidoDto) {
//
//        service.atualizarItemPedido(idItemPedido, itemPedidoDto);
//
//        return ResponseEntity.ok().build();
//
//    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoDto>> findAllItensPedidos() {

        //implementar Pageable

        try {
            List<ItemPedidoDto> itensPedidos = service.getAllItensPedidos();
            return ResponseEntity.ok(itensPedidos);

        } catch (NaoEncontradoException e) {

            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{idItemPedido}")
    public ResponseEntity<ItemPedidoDto> findItemPedidoById(@PathVariable Long idItemPedido){
        ItemPedidoDto itemPedido;

        try {
            itemPedido = service.getItemPedidoById(idItemPedido);
            return ResponseEntity.ok(itemPedido);
        } catch (NaoEncontradoException e) {

            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity deletePedido(@PathVariable Long idItemPedido) {

        service.deleteItemPedido(idItemPedido);

        return ResponseEntity.ok().build();
    }

}
