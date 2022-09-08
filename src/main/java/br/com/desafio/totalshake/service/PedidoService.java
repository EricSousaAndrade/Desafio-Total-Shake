package br.com.desafio.totalshake.service;

import br.com.desafio.totalshake.dto.PedidoDto;
import br.com.desafio.totalshake.exception.NaoEncontradoException;
import br.com.desafio.totalshake.exception.ParametroInvalidoException;
import br.com.desafio.totalshake.model.Pedido;
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
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public void salvarPedido(PedidoDto pedidoDto) throws Exception {

        LOGGER.info("Salvando pedido.");

        if (pedidoDto.getStatus() != null) {

            Pedido pedidoModel = modelMapper.map(pedidoDto, Pedido.class);
            repository.save(pedidoModel);

            LOGGER.info("Pedido salvo com sucesso.");
        } else {

            LOGGER.info("Parâmetro inválido.");
            throw new ParametroInvalidoException("Parâmetro inválido.");
        }

    }

    @Transactional
    public void atualizarPedido(Long idPedido, PedidoDto pedidoDto) {

        try {

            Pedido pedido = modelMapper.map(pedidoDto, Pedido.class);

            pedido.setId(idPedido);

            repository.save(pedido);

            LOGGER.info("Pedido de id {} atualizado com sucesso.", idPedido);

        } catch (Exception e) {

            LOGGER.warn("Não foi possível atualizar o pedido de id {} .", idPedido);
        }

    }

    public List<PedidoDto> getAllPedidos() throws NaoEncontradoException {

        List<Pedido> pedidoModel;

        if(!repository.findAll().isEmpty()){

            pedidoModel = repository.findAll();

            List<PedidoDto> listaPedidos =
                    modelMapper.map(pedidoModel, new TypeToken<List<PedidoDto>>() {}.getType());

            LOGGER.info("Pedidos encontrados com sucesso.");

            return listaPedidos;

        } else {

            LOGGER.warn("Pedidos não encontrados");
            throw new NaoEncontradoException("Pedidos não encontrados.");
        }

    }

    public PedidoDto getPedidoById(Long idPedido) throws NaoEncontradoException {

        PedidoDto pedido;

        if (!repository.findById(idPedido).isEmpty()) {

            Optional<Pedido> pedidoModel = repository.findById(idPedido);
            pedido = modelMapper.map(pedidoModel, PedidoDto.class);
            LOGGER.info("Pedido de id {} encontrado com sucesso.", idPedido);
        } else {
            LOGGER.warn("Pedido não encontrado");
            throw new NaoEncontradoException("Pedido não encontrado.");
        }

        return pedido;

    }

    public void deletePedido(Long idPedido) {

        Optional<Pedido> pedido = repository.findById(idPedido);

        if (pedido.isPresent()) {

            repository.deleteById(idPedido);
            LOGGER.info("Pedido deletado com sucesso.");

        } else {

            LOGGER.warn("Pedido não encontrado.");

        }

    }

}
