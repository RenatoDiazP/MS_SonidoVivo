package com.sonidovivo.ms_pedidos.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.sonidovivo.ms_pedidos.model.EstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    private Long idPedido;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private EstadoPedido estado;
    private LocalDateTime fechaCreacion;
    private List<DetalleResponseDTO> detalles;
}
