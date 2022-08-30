package br.com.desafio.totalshake.repositories;

import br.com.desafio.totalshake.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
