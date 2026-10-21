package com.munitour.backend.controller;

import com.munitour.backend.dto.request.UserUpdateRequestDto;
import com.munitour.backend.dto.response.UserResponseDto;
import com.munitour.backend.entity.AppUser;
import com.munitour.backend.mapper.UserMapper;
import com.munitour.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/users", "/api/v1/users"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Usuarios y Perfil", description = "Gestión del perfil del viajero, nivel ecomapas y preferencias")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Obtener el perfil del usuario autenticado actual")
    public ResponseEntity<UserResponseDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        AppUser user = userService.getByEmail(email);
        return ResponseEntity.ok(UserMapper.toResponseDto(user));
    }

    @PutMapping("/me")
    @Operation(summary = "Actualizar preferencias y datos del perfil del usuario")
    public ResponseEntity<UserResponseDto> updateCurrentUser(
            Authentication authentication,
            @RequestBody UserUpdateRequestDto request
    ) {
        String email = authentication.getName();
        AppUser updated = userService.updateProfile(email, request);
        return ResponseEntity.ok(UserMapper.toResponseDto(updated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener perfil de un usuario por id")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        AppUser user = userService.getById(id);
        return ResponseEntity.ok(UserMapper.toResponseDto(user));
    }
}
