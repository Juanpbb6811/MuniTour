package com.munitour.backend.controller;

import com.munitour.backend.dto.request.AuthRequestDto;
import com.munitour.backend.dto.request.GoogleAuthRequestDto;
import com.munitour.backend.dto.response.AuthResponseDto;
import com.munitour.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/auth", "/api/v1/auth"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro, login y autenticación con Google")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo viajero")
    public ResponseEntity<AuthResponseDto> register(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión con correo y contraseña")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/google")
    @Operation(summary = "Iniciar sesión o registrarse con Google OAuth (Google ID Token)")
    public ResponseEntity<AuthResponseDto> googleLogin(@RequestBody GoogleAuthRequestDto request) {
        return ResponseEntity.ok(authService.loginWithGoogle(request.getIdToken()));
    }
}
