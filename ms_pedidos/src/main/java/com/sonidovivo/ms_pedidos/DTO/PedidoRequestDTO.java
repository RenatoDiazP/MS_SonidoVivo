package com.sonidovivo.ms_pedidos.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {
    private Long direccionId;
    private List<DetalleRequestDTO> detalle;
}
