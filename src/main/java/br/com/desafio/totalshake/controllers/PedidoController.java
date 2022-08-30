package br.com.desafio.totalshake.controllers;

import br.com.desafio.totalshake.dtos.PedidoDto;
import br.com.desafio.totalshake.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<PedidoDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "dataHora") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<PedidoDto> list = pedidoService.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDto> findById(@PathVariable(value = "id") Long id) {
        PedidoDto pedidoDto = pedidoService.findById(id);
        return ResponseEntity.ok().body(pedidoDto);
    }

    @PostMapping
    public ResponseEntity<PedidoDto> insert(@RequestBody @Valid PedidoDto pedidoDto) {
        pedidoDto = pedidoService.insert(pedidoDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pedidoDto.getId()).toUri();
        return ResponseEntity.created(uri).body(pedidoDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PedidoDto> update(@PathVariable(value = "id") Long id, @RequestBody PedidoDto pedidoDto) {
        pedidoDto = pedidoService.update(id, pedidoDto);
        return ResponseEntity.ok().body(pedidoDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PedidoDto> deleteById(@PathVariable(value = "id") Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
