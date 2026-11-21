package com.sonidovivo.ms_clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estados_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre del estado es obligatorio")
    private String nombre; 
}