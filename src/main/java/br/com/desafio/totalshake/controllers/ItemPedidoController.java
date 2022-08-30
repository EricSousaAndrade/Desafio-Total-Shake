package br.com.desafio.totalshake.controllers;

import br.com.desafio.totalshake.dtos.ItemPedidoDto;
import br.com.desafio.totalshake.dtos.PedidoDto;
import br.com.desafio.totalshake.services.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/itens")
public class ItemPedidoController {

    @Autowired
    ItemPedidoService itemPedidoService;

    @GetMapping
    public ResponseEntity<Page<ItemPedidoDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "descricao") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<ItemPedidoDto> list = itemPedidoService.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemPedidoDto> findById(@PathVariable(value = "id") Long id) {
        ItemPedidoDto itemPedidoDto = itemPedidoService.findById(id);
        return ResponseEntity.ok().body(itemPedidoDto);
    }

    @PostMapping
    public ResponseEntity<ItemPedidoDto> insert(@RequestBody ItemPedidoDto itemPedidoDto) {
        itemPedidoDto = itemPedidoService.insert(itemPedidoDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(itemPedidoDto.getId()).toUri();
        return ResponseEntity.created(uri).body(itemPedidoDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ItemPedidoDto> update(@PathVariable(value = "id") Long id, @RequestBody ItemPedidoDto itemPedidoDto) {
        itemPedidoDto = itemPedidoService.update(id, itemPedidoDto);
        return ResponseEntity.ok().body(itemPedidoDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ItemPedidoDto> deleteById(@PathVariable(value = "id") Long id) {
        itemPedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
