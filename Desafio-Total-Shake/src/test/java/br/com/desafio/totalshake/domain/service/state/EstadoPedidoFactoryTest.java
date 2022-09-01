package br.com.desafio.totalshake.domain.service.state;

import br.com.desafio.totalshake.domain.model.Pedido;
import br.com.desafio.totalshake.domain.model.Status;
import br.com.desafio.totalshake.domain.service.state.EstadoPedido;
import br.com.desafio.totalshake.domain.service.state.EstadoPedidoFactory;
import br.com.desafio.totalshake.domain.service.state.impl.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EstadoPedidoFactoryTest {

    private final Pedido pedido = new Pedido();

    @Test
    public void deve_retornarUmaInstanciaDoTipo_CriadoImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.CRIADO, pedido);

        assertTrue(estadoPedido instanceof CriadoImpl);
    }

    @Test
    public void deve_retornarUmaInstanciaDoTipo_RealizadoImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.REALIZADO, pedido);

        assertTrue(estadoPedido instanceof RealizadoImpl);
    }

    @Test
    public void deve_retornarUmaInstanciaDoTipo_PagoImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.PAGO, pedido);

        assertTrue(estadoPedido instanceof PagoImpl);
    }

    @Test
    public void deve_retornarUmaInstanciaDoTipo_ConfirmadoImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.CONFIRMADO, pedido);

        assertTrue(estadoPedido instanceof ConfirmadoImpl);
    }

    @Test
    public void deve_retornarUmaInstanciaDoTipo_ProntoImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.PRONTO, pedido);

        assertTrue(estadoPedido instanceof ProntoImpl);
    }

    @Test
    public void deve_retornarUmaInstanciaDoTipo_SaiuParaEntregaImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.SAIU_PARA_ENTREGA, pedido);

        assertTrue(estadoPedido instanceof SaiuParaEntregaImpl);
    }
    @Test
    public void deve_retornarUmaInstanciaDoTipo_EntregueImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.ENTREGUE, pedido);

        assertTrue(estadoPedido instanceof EntregueImpl);
    }


    @Test
    public void deve_retornarUmaInstanciaDoTipo_NaoAutorizadoImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.NAO_AUTORIZADO, pedido);

        assertTrue(estadoPedido instanceof NaoAutorizadoImpl);
    }

    @Test
    public void deve_retornarUmaInstanciaDoTipo_CanceladoImpl(){
        EstadoPedido estadoPedido = EstadoPedidoFactory.ofStatus(Status.CANCELADO, pedido);

        assertTrue(estadoPedido instanceof CanceladoImpl);
    }
}
