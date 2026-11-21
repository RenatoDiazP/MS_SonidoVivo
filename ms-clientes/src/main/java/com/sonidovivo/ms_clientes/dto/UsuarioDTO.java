package com.sonidovivo.ms_clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;
    private RolDTO rol;
    private EstadoDTO estado;
}