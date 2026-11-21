package com.sonidovivo.ms_producto.service;

import com.sonidovivo.ms_producto.dto.CategoriaDTO;
import com.sonidovivo.ms_producto.exception.ResourceNotFoundException;
import com.sonidovivo.ms_producto.model.Categoria;
import com.sonidovivo.ms_producto.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public CategoriaDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        return toDTO(categoria);
    }

    public CategoriaDTO crear(CategoriaDTO dto) {
        if (categoriaRepository.existsByNombre(dto.getNombre().trim())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + dto.getNombre());
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre().trim());
        categoria.setDescripcion(dto.getDescripcion());
        return toDTO(categoriaRepository.save(categoria));
    }

    public CategoriaDTO actualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));

        String nuevoNombre = dto.getNombre().trim();
        if (!categoria.getNombre().equalsIgnoreCase(nuevoNombre) && categoriaRepository.existsByNombre(nuevoNombre)) {
            throw new IllegalArgumentException("Ya existe otra categoría con el nombre: " + nuevoNombre);
        }

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(dto.getDescripcion());
        return toDTO(categoriaRepository.save(categoria));
    }

    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO toDTO(Categoria c) {
        return new CategoriaDTO(c.getId(), c.getNombre(), c.getDescripcion());
    }
}