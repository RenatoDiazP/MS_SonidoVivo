package com.sonidovivo.ms_pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sonidovivo.ms_pedidos.model.DetallePedido;

@Repository 
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

}
