package br.com.desafio.totalshake.service;

import br.com.desafio.totalshake.dto.PedidoDto;
import br.com.desafio.totalshake.exception.NaoEncontradoException;
import br.com.desafio.totalshake.exception.ParametroInvalidoException;
import br.com.desafio.totalshake.model.Pedido;
import br.com.desafio.totalshake.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    @Transactional
    public void salvarPedido(PedidoDto pedidoDto) throws Exception {

        LOGGER.info("Salvando pedido.");

        if (pedidoDto.getStatus() != null) {

            Pedido pedidoModel = converterPedidoParaModel(pedidoDto);
            repository.save(pedidoModel);
        } else {

            LOGGER.info("Parâmetro inválido.");
            throw new ParametroInvalidoException("Parâmetro inválido.");
        }

    }

    @Transactional
    public void atualizarPedido(Long idPedido, PedidoDto pedidoDto) {

        try {

            Pedido pedido = converterPedidoParaModel(pedidoDto);

            repository.deleteById(idPedido);
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

            return converterListaPedidoParaDto(pedidoModel);

        } else {

            LOGGER.warn("Pedidos não encontrados");
            throw new NaoEncontradoException("Pedidos não encontrados.");
        }

    }

    public PedidoDto getPedidoById(Long idPedido) throws NaoEncontradoException {

        PedidoDto pedido;

        if (!repository.findById(idPedido).isEmpty()) {

            Optional<Pedido> pedidoModel = repository.findById(idPedido);
            pedido = converterPedidoParaDto(pedidoModel);
            LOGGER.info("Pedido de id {} encontrado com sucesso.", idPedido);
        } else {
            LOGGER.warn("Pedido não encontrado");
            throw new NaoEncontradoException("Pedido não encontrado.");
        }

        return pedido;

    }

    public void deletePedido(Long idPedido) {

        Optional<Pedido> pedido = repository.findById(idPedido);

        //implementar exceções

        if (pedido.isPresent()) {

            repository.deleteById(idPedido);
            LOGGER.info("Pedido deletado com sucesso.");

        } else {

            LOGGER.warn("Pedido não encontrado.");

        }

    }

    private Pedido converterPedidoParaModel(PedidoDto pedidoDto) {

        Pedido pedidoModel = new Pedido();

        pedidoModel.setItensPedidosList(pedidoDto.getItensPedidosList());
        pedidoModel.setStatus(pedidoDto.getStatus());
        pedidoModel.setDataHora(pedidoDto.getDataHora());

        return pedidoModel;
    }

    private PedidoDto converterPedidoParaDto(Optional<Pedido> pedidoModel) {

        PedidoDto pedidoDto = new PedidoDto();

        pedidoDto.setDataHora(pedidoModel.get().getDataHora());
        pedidoDto.setItensPedidosList(pedidoModel.get().getItensPedidosList());
        pedidoDto.setStatus(pedidoModel.get().getStatus());

        return pedidoDto;
    }

    private List<PedidoDto> converterListaPedidoParaDto(List<Pedido> pedidosModel) {

        List<PedidoDto> pedidoDto = new ArrayList<>();

        pedidosModel.forEach(pedido -> {
            PedidoDto dto = new PedidoDto();

            dto.setDataHora(pedido.getDataHora());
            dto.setItensPedidosList(pedido.getItensPedidosList());
            dto.setStatus(pedido.getStatus());

            pedidoDto.add(dto);

        });

        return pedidoDto;
    }

}
