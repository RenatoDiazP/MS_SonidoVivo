package com.sonidovivo.ms_pedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sonidovivo.ms_pedidos.DTO.PedidoRequestDTO;
import com.sonidovivo.ms_pedidos.DTO.PedidoResponseDTO;
import com.sonidovivo.ms_pedidos.model.EstadoPedido;
import com.sonidovivo.ms_pedidos.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @GetMapping
    @Operation(summary = "Lista los pedidos en la base de datos")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos(){
        return ResponseEntity.ok(service.listarPedidos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca un pedido por su id")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id){
        PedidoResponseDTO pedido = service.buscarPorId(id);

        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crea un pedido y lo inserta en la base de datos") 
    public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO dto){
        PedidoResponseDTO respuesta = service.crearPedido(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}/estado")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualiza el estado de un pedido")
    public ResponseEntity<PedidoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoPedido estado
    ){

        return ResponseEntity.ok(
                service.actualizarEstado(id, estado)
        );
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Elimina un pedido, buscandolo por id")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id){
        service.eliminarPedido(id);

        return ResponseEntity.noContent().build();
    }
}
