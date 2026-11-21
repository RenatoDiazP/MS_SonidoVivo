package com.sonidovivo.ms_producto.controller;

import com.sonidovivo.ms_producto.dto.ProductoRequestDTO;
import com.sonidovivo.ms_producto.dto.ProductoResponseDTO;
import com.sonidovivo.ms_producto.service.ProductoService;
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
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Catálogo de instrumentos musicales y control de stock")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar productos activos del catálogo")
    public ResponseEntity<List<ProductoResponseDTO>> listarActivos() {
        return ResponseEntity.ok(productoService.listarActivos());
    }

    @GetMapping("/todos")
    @Operation(summary = "Listar todos los productos (incluye inactivos)")
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de un producto por ID")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Filtrar productos activos por categoría")
    public ResponseEntity<List<ProductoResponseDTO>> listarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(productoService.listarPorCategoria(categoriaId));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos activos por nombre")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear un nuevo instrumento en el catálogo (Solo ADMIN)")
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(dto));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar datos o stock de un producto (Solo ADMIN)")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    @PutMapping("/{id}/descontar-stock")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Descontar unidades de stock al confirmar una compra")
    public ResponseEntity<ProductoResponseDTO> descontarStock(@PathVariable Long id,
                                                             @RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.descontarStock(id, cantidad));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Desactivar producto del catálogo (Solo ADMIN)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}