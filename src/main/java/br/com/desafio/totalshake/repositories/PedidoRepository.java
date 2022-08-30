package br.com.desafio.totalshake.repositories;

import br.com.desafio.totalshake.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
