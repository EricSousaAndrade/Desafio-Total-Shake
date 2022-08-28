package br.com.desafio.totalshake.domain.repository;

import br.com.desafio.totalshake.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
