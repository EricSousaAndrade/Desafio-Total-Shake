package br.com.desafio.totalshake.services;

import br.com.desafio.totalshake.dtos.ItemPedidoDto;
import br.com.desafio.totalshake.entities.ItemPedido;
import br.com.desafio.totalshake.repositories.ItemPedidoRepository;
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
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Transactional(readOnly = true)
    public Page<ItemPedidoDto> findAllPaged(PageRequest pageRequest) {
        Page<ItemPedido> listaItensDoPedido = itemPedidoRepository.findAll(pageRequest);
        return listaItensDoPedido.map(ItemPedidoDto::new);
    }

    @Transactional(readOnly = true)
    public ItemPedidoDto findById(Long id) {
        Optional<ItemPedido> itemPedidoOptional = itemPedidoRepository.findById(id);
        ItemPedido entidade = itemPedidoOptional
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("O Recurso com id %s nao foi encontrado.", id)));
        return new ItemPedidoDto(entidade);
    }

    @Transactional(readOnly = true)
    public ItemPedidoDto insert(ItemPedidoDto dto) {
        ItemPedido entidade = new ItemPedido();
        entidade.setQuantidade(dto.getQuantidade());
        entidade.setDescricao(dto.getDescricao());
        entidade.setPedido(dto.getPedido());
        entidade = itemPedidoRepository.save(entidade);
        return new ItemPedidoDto(entidade);
    }

    public void deleteById(Long id) {
        try {
            itemPedidoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNaoEncontradoException((String.format("O recurso com id %s nao foi encontrado.", id)));
        }
    }

    @Transactional
    public ItemPedidoDto update(Long id, ItemPedidoDto dto) {
        try {
            ItemPedido entidade = itemPedidoRepository.getReferenceById(id);
            entidade.setQuantidade(dto.getQuantidade());
            entidade.setDescricao(dto.getDescricao());
            entidade.setPedido(dto.getPedido());
            entidade = itemPedidoRepository.save(entidade);
            return new ItemPedidoDto(entidade);

        } catch (EntityNotFoundException exception) {
            throw new RecursoNaoEncontradoException(String.format("O Recurso com id %s nao foi encontrado.", id));
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseException("Violacao de integridade.");
        }
    }
}
