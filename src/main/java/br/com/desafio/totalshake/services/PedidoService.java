package br.com.desafio.totalshake.services;

import br.com.desafio.totalshake.dtos.ItemPedidoDto;
import br.com.desafio.totalshake.dtos.PedidoDto;
import br.com.desafio.totalshake.entities.ItemPedido;
import br.com.desafio.totalshake.entities.Pedido;
import br.com.desafio.totalshake.repositories.ItemPedidoRepository;
import br.com.desafio.totalshake.repositories.PedidoRepository;
import br.com.desafio.totalshake.services.exceptions.DatabaseException;
import br.com.desafio.totalshake.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Transactional(readOnly = true)
    public Page<PedidoDto> findAllPaged(PageRequest pageRequest) {
        Page<Pedido> listaPedido = pedidoRepository.findAll(pageRequest);
        return listaPedido.map(PedidoDto::new);
    }

    @Transactional(readOnly = true)
    public PedidoDto findById(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        Pedido pedido = pedidoOptional
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("O recurso com id %s nao foi encontrado.", id)));
        return new PedidoDto(pedido);
    }

    @Transactional(readOnly = true)
    public PedidoDto insert(PedidoDto dto) {
        Pedido entidade = new Pedido();
        toDto(dto, entidade);
        entidade = pedidoRepository.save(entidade);
        return new PedidoDto(entidade);
    }

    public void deleteById(Long id) {
        try {
            pedidoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNaoEncontradoException((String.format("O recurso com id %s nao foi encontrado.", id)));
        }
    }

    @Transactional
    public PedidoDto update(Long id, PedidoDto dto) {
        try {
            Pedido entidade = pedidoRepository.getReferenceById(id);
            toDto(dto, entidade);
            entidade = pedidoRepository.save(entidade);
            return new PedidoDto(entidade);
        } catch (EntityNotFoundException exception) {
            throw new RecursoNaoEncontradoException(String.format("O Recurso com id %s nao foi encontrado.", id));
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseException("Violacao de integridade.");
        }
    }

    private void toDto(PedidoDto dto, Pedido entity) {
        entity.setId(dto.getId());
        entity.setDataHora(dto.getDataHora());
        entity.setStatus(dto.getStatus());

        entity.getItensDoPedido().clear();
        for (ItemPedidoDto itemPedidoDto : dto.getItensDoPedido()) {
            ItemPedido itemPedido = itemPedidoRepository.getReferenceById(itemPedidoDto.getId());
            entity.getItensDoPedido().add(itemPedido);
        }
    }
}
