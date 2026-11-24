package com.sonidovivo.ms_pedidos.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private BigDecimal precio;
}
