package com.sonidovivo.ms_contacto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes_contacto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(length = 25)
    private String telefono;

    @Column(nullable = false, length = 150)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoMensaje estado;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) {
            this.estado = EstadoMensaje.PENDIENTE;
        }
        if (this.fechaEnvio == null) {
            this.fechaEnvio = LocalDateTime.now();
        }
    }
}