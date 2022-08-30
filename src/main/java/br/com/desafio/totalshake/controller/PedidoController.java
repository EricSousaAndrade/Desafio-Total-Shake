package br.com.desafio.totalshake.controller;

import br.com.desafio.totalshake.dto.PedidoDto;
import br.com.desafio.totalshake.exception.NaoEncontradoException;
import br.com.desafio.totalshake.exception.ParametroInvalidoException;
import br.com.desafio.totalshake.model.Pedido;
import br.com.desafio.totalshake.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoService service;

    @PostMapping()
    public ResponseEntity<Pedido> postPedido(@RequestBody PedidoDto pedido) throws Exception {

        try{
            service.salvarPedido(pedido);
            return ResponseEntity.ok().build();

        } catch (ParametroInvalidoException e){

            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{idPedido}")
    public ResponseEntity putPedido(@PathVariable Long idPedido, @RequestBody PedidoDto pedido) {

        service.atualizarPedido(idPedido, pedido);

        return ResponseEntity.ok().build();

    }

    @GetMapping
    public ResponseEntity<List<PedidoDto>> findAllPedidos() {

        //implementar Pageable

        try {
            List<PedidoDto> pedidos = service.getAllPedidos();
            return ResponseEntity.ok(pedidos);

        } catch (NaoEncontradoException e) {

            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDto> findPedidoById(@PathVariable Long idPedido){
        PedidoDto pedido;

        try {
            pedido = service.getPedidoById(idPedido);
            return ResponseEntity.ok(pedido);
        } catch (NaoEncontradoException e) {

            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity deletePedido(@PathVariable Long idPedido) {

        service.deletePedido(idPedido);

        return ResponseEntity.ok().build();
    }

}
