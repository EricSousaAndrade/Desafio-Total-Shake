package br.com.desafio.totalshake.service;

import br.com.desafio.totalshake.dto.ItemPedidoDto;
import br.com.desafio.totalshake.exception.NaoEncontradoException;
import br.com.desafio.totalshake.exception.ParametroInvalidoException;
import br.com.desafio.totalshake.model.ItemPedido;
import br.com.desafio.totalshake.repository.ItemPedidoRepository;
import br.com.desafio.totalshake.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemPedidoService.class);

    ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public void salvarItemPedido(ItemPedidoDto itemPedidoDto) throws Exception {

        LOGGER.info("Salvando itemPedido.");

        if (itemPedidoDto != null) {

            ItemPedido itemPedidoModel = modelMapper.map(itemPedidoDto, ItemPedido.class);

            repository.save(itemPedidoModel);

            LOGGER.info("itemPedido salvo com sucesso.");
        } else {

            LOGGER.info("Parâmetro inválido.");
            throw new ParametroInvalidoException("Parâmetro inválido.");
        }

    }

    @Transactional
    public void atualizarItemPedido(Long idItemPedido, ItemPedidoDto itemPedidoDto) {

        try {

            ItemPedido itemPedido = modelMapper.map(itemPedidoDto, ItemPedido.class);

            itemPedido.setId(idItemPedido);

            repository.save(itemPedido);

            LOGGER.info("Pedido de id {} atualizado com sucesso.", idItemPedido);

        } catch (Exception e) {

            LOGGER.warn("Não foi possível atualizar o itemPedido de id {} .", idItemPedido);
        }

    }

    public List<ItemPedidoDto> findAllItensPedidos() throws NaoEncontradoException {

        List<ItemPedido> itemPedidoModel;

        if(!repository.findAll().isEmpty()){

            itemPedidoModel = repository.findAll();


            List<ItemPedidoDto> listaItensPedidos =
                    modelMapper.map(itemPedidoModel, new TypeToken<List<ItemPedidoDto>>() {}.getType());

            LOGGER.info("Itens Pedidos encontrados com sucesso.");

            return listaItensPedidos;

        } else {

            LOGGER.warn("Itens Pedidos não encontrados");
            throw new NaoEncontradoException("Itens Pedidos não encontrados.");
        }

    }

    public ItemPedidoDto findItemPedidoById(Long idItemPedido) throws NaoEncontradoException {

        ItemPedidoDto itemPedido;

        if (!repository.findById(idItemPedido).isEmpty()) {

            Optional<ItemPedido> itemPedidoModel = repository.findById(idItemPedido);


            itemPedido = modelMapper.map(itemPedidoModel, ItemPedidoDto.class);
            LOGGER.info("Item Pedido de id {} encontrado com sucesso.", idItemPedido);
        } else {
            LOGGER.warn("Item Pedido não encontrado");
            throw new NaoEncontradoException("Item Pedido não encontrado.");
        }

        return itemPedido;

    }

    public void deleteItemPedido(Long idItemPedido) {

        Optional<ItemPedido> itemPedido = repository.findById(idItemPedido);

        if (itemPedido.isPresent()) {

            repository.deleteById(idItemPedido);
            LOGGER.info("Item Pedido deletado com sucesso.");

        } else {

            LOGGER.warn("Item Pedido não encontrado.");
        }

    }

}
