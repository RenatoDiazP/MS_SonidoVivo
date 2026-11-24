package com.sonidovivo.ms_contacto.dto;

import com.sonidovivo.ms_contacto.model.EstadoMensaje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactoResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String asunto;
    private String mensaje;
    private EstadoMensaje estado;
    private LocalDateTime fechaEnvio;
}