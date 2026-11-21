package com.sonidovivo.ms_clientes.service;

import com.sonidovivo.ms_clientes.dto.EstadoDTO;
import com.sonidovivo.ms_clientes.dto.RolDTO;
import com.sonidovivo.ms_clientes.dto.UsuarioDTO;
import com.sonidovivo.ms_clientes.model.EstadoUsuario;
import com.sonidovivo.ms_clientes.model.Rol;
import com.sonidovivo.ms_clientes.model.Usuario;
import com.sonidovivo.ms_clientes.repository.EstadoUsuarioRepository;
import com.sonidovivo.ms_clientes.repository.RolRepository;
import com.sonidovivo.ms_clientes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final EstadoUsuarioRepository estadoRepo;

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepo.findAll().stream().map(this::toDTO).toList();
    }

    public UsuarioDTO obtenerPorId(Long id) {
        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toDTO(u);
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        u.setNombre(dto.getNombre());
        u.setTelefono(dto.getTelefono());

        if (dto.getRol() != null && dto.getRol().getId() != null) {
            Rol rol = rolRepo.findById(dto.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no válido"));
            u.setRol(rol);
        }
        if (dto.getEstado() != null && dto.getEstado().getId() != null) {
            EstadoUsuario estado = estadoRepo.findById(dto.getEstado().getId())
                    .orElseThrow(() -> new RuntimeException("Estado no válido"));
            u.setEstado(estado);
        }

        return toDTO(usuarioRepo.save(u));
    }

    public void eliminar(Long id) {
        if (!usuarioRepo.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepo.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getTelefono(),
                u.getFechaRegistro(),
                new RolDTO(u.getRol().getId(), u.getRol().getNombre()),
                new EstadoDTO(u.getEstado().getId(), u.getEstado().getNombre())
        );
    }
}