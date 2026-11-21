package com.sonidovivo.ms_producto.service;

import com.sonidovivo.ms_producto.dto.ProductoRequestDTO;
import com.sonidovivo.ms_producto.dto.ProductoResponseDTO;
import com.sonidovivo.ms_producto.exception.ResourceNotFoundException;
import com.sonidovivo.ms_producto.exception.StockInsuficienteException;
import com.sonidovivo.ms_producto.model.Categoria;
import com.sonidovivo.ms_producto.model.Producto;
import com.sonidovivo.ms_producto.repository.CategoriaRepository;
import com.sonidovivo.ms_producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<ProductoResponseDTO> listarActivos() {
        return productoRepository.findByActivoTrue().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return toDTO(producto);
    }

    public List<ProductoResponseDTO> listarPorCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoría no encontrada con ID: " + categoriaId);
        }
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre).stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + dto.getCategoriaId()));

        Producto producto = new Producto();
        mapearDatos(producto, dto, categoria);
        return toDTO(productoRepository.save(producto));
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + dto.getCategoriaId()));

        mapearDatos(producto, dto, categoria);
        return toDTO(productoRepository.save(producto));
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Transactional
    public ProductoResponseDTO descontarStock(Long id, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a descontar debe ser mayor a 0");
        }

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new StockInsuficienteException("El producto '" + producto.getNombre() + "' no está disponible");
        }

        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente para '" + producto.getNombre() + "'. Disponible: " + producto.getStock());
        }

        producto.setStock(producto.getStock() - cantidad);
        return toDTO(productoRepository.save(producto));
    }

    private void mapearDatos(Producto producto, ProductoRequestDTO dto, Categoria categoria) {
        producto.setNombre(dto.getNombre().trim());
        producto.setMarca(dto.getMarca().trim());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        producto.setCategoria(categoria);
    }

    private ProductoResponseDTO toDTO(Producto p) {
        return new ProductoResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getMarca(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getImagenUrl(),
                p.getActivo(),
                p.getCategoria().getId(),
                p.getCategoria().getNombre()
        );
    }
}