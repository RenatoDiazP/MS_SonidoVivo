package com.sonidovivo.ms_clientes.controller;

import com.sonidovivo.ms_clientes.dto.AuthResponse;
import com.sonidovivo.ms_clientes.dto.LoginRequest;
import com.sonidovivo.ms_clientes.dto.RegisterRequest;
import com.sonidovivo.ms_clientes.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Registro, login y renovación de tokens JWT")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo cliente")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registrar(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Renovar un token JWT activo")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authService.renovarToken(authHeader));
    }
}