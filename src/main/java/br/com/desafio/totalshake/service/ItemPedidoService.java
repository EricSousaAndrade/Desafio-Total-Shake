package br.com.desafio.totalshake.service;

import br.com.desafio.totalshake.dto.ItemPedidoDto;
import br.com.desafio.totalshake.exception.NaoEncontradoException;
import br.com.desafio.totalshake.exception.ParametroInvalidoException;
import br.com.desafio.totalshake.model.ItemPedido;
import br.com.desafio.totalshake.repository.ItemPedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private PedidoService pedidoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemPedidoService.class);

//    @Transactional
//    public void salvarItemPedido(ItemPedidoDto itemPedidoDto) throws Exception {
//
//        LOGGER.info("Salvando itemPedido.");
//
//        if (itemPedidoDto != null) {
//
//            ItemPedido itemPedidoModel = converterItemPedidoParaModel(itemPedidoDto);
//            repository.save(itemPedidoModel);
//
//            LOGGER.info("itemPedido salvo com sucesso.");
//        } else {
//
//            LOGGER.info("Parâmetro inválido.");
//            throw new ParametroInvalidoException("Parâmetro inválido.");
//        }
//
//    }

//    @Transactional
//    public void atualizarItemPedido(Long idItemPedido, ItemPedidoDto itemPedidoDto) {
//
//        try {
//
//            ItemPedido itemPedido = converterItemPedidoParaModel(itemPedidoDto);
//
//            repository.deleteById(idItemPedido);
//            repository.save(itemPedido);
//
//            LOGGER.info("Pedido de id {} atualizado com sucesso.", idItemPedido);
//
//        } catch (Exception e) {
//
//            LOGGER.warn("Não foi possível atualizar o itemPedido de id {} .", idItemPedido);
//        }
//
//    }

    public List<ItemPedidoDto> getAllItensPedidos() throws NaoEncontradoException {

        List<ItemPedido> itemPedidoModel;

        if(!repository.findAll().isEmpty()){

            itemPedidoModel = repository.findAll();

            return converterListaItemPedidoParaDto(itemPedidoModel);

        } else {

            LOGGER.warn("Itens Pedidos não encontrados");
            throw new NaoEncontradoException("Itens Pedidos não encontrados.");
        }

    }

    public ItemPedidoDto getItemPedidoById(Long idItemPedido) throws NaoEncontradoException {

        ItemPedidoDto itemPedido;

        if (!repository.findById(idItemPedido).isEmpty()) {

            Optional<ItemPedido> itemPedidoModel = repository.findById(idItemPedido);
            itemPedido = converterItemPedidoParaDto(itemPedidoModel);
            LOGGER.info("Item Pedido de id {} encontrado com sucesso.", idItemPedido);
        } else {
            LOGGER.warn("Item Pedido não encontrado");
            throw new NaoEncontradoException("Item Pedido não encontrado.");
        }

        return itemPedido;

    }

    public void deleteItemPedido(Long idItemPedido) {

        Optional<ItemPedido> itemPedido = repository.findById(idItemPedido);

        //implementar exceções

        if (itemPedido.isPresent()) {

            repository.deleteById(idItemPedido);
            LOGGER.info("Item Pedido deletado com sucesso.");

        } else {

            LOGGER.warn("Item Pedido não encontrado.");
        }

    }

    private ItemPedido converterItemPedidoParaModel(ItemPedidoDto itemPedidoDto) {

        ItemPedido itemPedidoModel = new ItemPedido();

        itemPedidoModel.setPedido(itemPedidoDto.getPedido());
        itemPedidoModel.setDescricao(itemPedidoDto.getDescricao());
        itemPedidoModel.setQuantidade(itemPedidoDto.getQuantidade());

        return itemPedidoModel;
    }

    private ItemPedidoDto converterItemPedidoParaDto(Optional<ItemPedido> itemPedidoModel) {

        ItemPedidoDto itemPedidoDto = new ItemPedidoDto();

        itemPedidoDto.setDescricao(itemPedidoModel.get().getDescricao());
        itemPedidoDto.setPedido(itemPedidoModel.get().getPedido());
        itemPedidoDto.setQuantidade(itemPedidoModel.get().getQuantidade());

        return itemPedidoDto;
    }

    private List<ItemPedidoDto> converterListaItemPedidoParaDto(List<ItemPedido> itensPedidosModel) {

        List<ItemPedidoDto> itemPedidoDto = new ArrayList<>();

        itemPedidoDto.forEach(itemPedido -> {
            ItemPedidoDto dto = new ItemPedidoDto();

            dto.setPedido(itemPedido.getPedido());
            dto.setQuantidade(itemPedido.getQuantidade());
            dto.setDescricao(itemPedido.getDescricao());

            itemPedidoDto.add(dto);

        });

        return itemPedidoDto;
    }

}
