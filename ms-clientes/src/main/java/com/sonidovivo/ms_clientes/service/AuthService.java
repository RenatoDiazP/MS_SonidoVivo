package com.sonidovivo.ms_clientes.service;

import com.sonidovivo.ms_clientes.dto.AuthResponse;
import com.sonidovivo.ms_clientes.dto.LoginRequest;
import com.sonidovivo.ms_clientes.dto.RegisterRequest;
import com.sonidovivo.ms_clientes.model.EstadoUsuario;
import com.sonidovivo.ms_clientes.model.Rol;
import com.sonidovivo.ms_clientes.model.Usuario;
import com.sonidovivo.ms_clientes.repository.EstadoUsuarioRepository;
import com.sonidovivo.ms_clientes.repository.RolRepository;
import com.sonidovivo.ms_clientes.repository.UsuarioRepository;
import com.sonidovivo.ms_clientes.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final EstadoUsuarioRepository estadoRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse registrar(RegisterRequest req) {
        if (usuarioRepo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        Rol rolCliente = rolRepo.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));
        EstadoUsuario estadoActivo = estadoRepo.findByNombre("ACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado ACTIVO no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNombre(req.getNombre());
        usuario.setEmail(req.getEmail());
        usuario.setPassword(passwordEncoder.encode(req.getPassword()));
        usuario.setTelefono(req.getTelefono());
        usuario.setRol(rolCliente);
        usuario.setEstado(estadoActivo);

        Usuario guardado = usuarioRepo.save(usuario);
        String token = jwtService.generarToken(guardado.getId(), guardado.getEmail(), rolCliente.getNombre());

        return new AuthResponse(token, guardado.getId(), guardado.getNombre(), guardado.getEmail(), rolCliente.getNombre());
    }

    public AuthResponse login(LoginRequest req) {
        Usuario usuario = usuarioRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(req.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        if (!"ACTIVO".equals(usuario.getEstado().getNombre())) {
            throw new RuntimeException("Usuario inactivo o bloqueado");
        }

        String token = jwtService.generarToken(usuario.getId(), usuario.getEmail(), usuario.getRol().getNombre());
        return new AuthResponse(token, usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol().getNombre());
    }

    public AuthResponse renovarToken(String authHeader) {
        String tokenActual = authHeader.substring(7);
        String email = jwtService.extraerEmail(tokenActual);
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String nuevoToken = jwtService.generarToken(usuario.getId(), usuario.getEmail(), usuario.getRol().getNombre());
        return new AuthResponse(nuevoToken, usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol().getNombre());
    }
}