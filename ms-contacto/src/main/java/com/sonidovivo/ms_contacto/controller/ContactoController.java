package com.sonidovivo.ms_contacto.controller;

import com.sonidovivo.ms_contacto.dto.ContactoRequestDTO;
import com.sonidovivo.ms_contacto.dto.ContactoResponseDTO;
import com.sonidovivo.ms_contacto.model.EstadoMensaje;
import com.sonidovivo.ms_contacto.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacto")
@RequiredArgsConstructor
@Tag(name = "Contacto", description = "Formulario de contacto y bandeja de mensajes")
public class ContactoController {

    private final ContactoService contactoService;

    @PostMapping
    @Operation(summary = "Enviar formulario de contacto (Público)")
    public ResponseEntity<ContactoResponseDTO> enviar(@Valid @RequestBody ContactoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactoService.enviarMensaje(dto));
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar todos los mensajes recibidos (Solo ADMIN)")
    public ResponseEntity<List<ContactoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(contactoService.listarTodos());
    }

    @GetMapping("/estado/{estado}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Filtrar mensajes por estado: PENDIENTE, LEIDO o RESPONDIDO (Solo ADMIN)")
    public ResponseEntity<List<ContactoResponseDTO>> listarPorEstado(@PathVariable EstadoMensaje estado) {
        return ResponseEntity.ok(contactoService.listarPorEstado(estado));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obtener detalle de un mensaje por ID (Solo ADMIN)")
    public ResponseEntity<ContactoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contactoService.obtenerPorId(id));
    }

    @PutMapping("/{id}/estado")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar estado del mensaje (Solo ADMIN)")
    public ResponseEntity<ContactoResponseDTO> cambiarEstado(@PathVariable Long id,
                                                             @RequestParam EstadoMensaje estado) {
        return ResponseEntity.ok(contactoService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar mensaje de contacto (Solo ADMIN)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contactoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}