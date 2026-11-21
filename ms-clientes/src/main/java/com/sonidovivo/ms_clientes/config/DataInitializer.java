package com.sonidovivo.ms_clientes.config;

import com.sonidovivo.ms_clientes.model.EstadoUsuario;
import com.sonidovivo.ms_clientes.model.Rol;
import com.sonidovivo.ms_clientes.model.Usuario;
import com.sonidovivo.ms_clientes.repository.EstadoUsuarioRepository;
import com.sonidovivo.ms_clientes.repository.RolRepository;
import com.sonidovivo.ms_clientes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepo;
    private final EstadoUsuarioRepository estadoRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Rol adminRol = rolRepo.findByNombre("ADMIN")
                .orElseGet(() -> rolRepo.save(new Rol(null, "ADMIN")));
        rolRepo.findByNombre("CLIENTE")
                .orElseGet(() -> rolRepo.save(new Rol(null, "CLIENTE")));

        EstadoUsuario activo = estadoRepo.findByNombre("ACTIVO")
                .orElseGet(() -> estadoRepo.save(new EstadoUsuario(null, "ACTIVO")));
        estadoRepo.findByNombre("INACTIVO")
                .orElseGet(() -> estadoRepo.save(new EstadoUsuario(null, "INACTIVO")));

        if (!usuarioRepo.existsByEmail("admin@sonidovivo.cl")) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin SonidoVivo");
            admin.setEmail("admin@sonidovivo.cl");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setTelefono("+56912345678");
            admin.setRol(adminRol);
            admin.setEstado(activo);
            usuarioRepo.save(admin);
        }
    }
}