package com.sonidovivo.ms_pedidos.mapper;

import org.springframework.stereotype.Component;

import com.sonidovivo.ms_pedidos.DTO.PedidoResponseDTO;
import com.sonidovivo.ms_pedidos.model.Pedido;

@Component
public class PedidoMapper {
    public PedidoResponseDTO toDto(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();

        dto.setIdPedido(pedido.getIdPedido());
        dto.setSubtotal(pedido.getSubtotal());
        dto.setIva(pedido.getIva());
        dto.setTotal(pedido.getTotal());
        dto.setEstado(pedido.getEstado());
        dto.setFechaCreacion(pedido.getFechaCreacion());

        return dto;
    }
}
