package com.munitour.backend.service;

import com.munitour.backend.dto.request.AuthRequestDto;
import com.munitour.backend.dto.response.AuthResponseDto;
import com.munitour.backend.entity.AppUser;
import com.munitour.backend.exception.BadRequestException;
import com.munitour.backend.exception.ResourceNotFoundException;
import com.munitour.backend.repository.UserRepository;
import com.munitour.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate;

    public AuthResponseDto register(AuthRequestDto request) {
        if (!StringUtils.hasText(request.getEmail())) {
            throw new BadRequestException("El correo electrónico es obligatorio");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Ya existe una cuenta registrada con este correo electrónico");
        }

        String rawPassword = StringUtils.hasText(request.getPassword()) ? request.getPassword() : "12345678";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        String fullName = StringUtils.hasText(request.getName())
                ? request.getName()
                : request.getEmail().substring(0, request.getEmail().indexOf('@'));

        AppUser user = AppUser.builder()
                .id("usr_" + UUID.randomUUID().toString().substring(0, 8))
                .email(request.getEmail().toLowerCase().trim())
                .passwordHash(encodedPassword)
                .fullName(fullName)
                .originCity("Cajicá, Cundinamarca")
                .favoriteCoffeeMethod("Filtrado Chemex")
                .avatarEmoji("🧭")
                .travelerLevel("Explorador Cittaslow")
                .ecoPoints(100)
                .build();

        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

        return AuthResponseDto.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getFullName())
                .email(user.getEmail())
                .travelerLevel(user.getTravelerLevel())
                .build();
    }

    public AuthResponseDto login(AuthRequestDto request) {
        if (!StringUtils.hasText(request.getEmail())) {
            throw new BadRequestException("El correo electrónico es obligatorio");
        }

        AppUser user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una cuenta con el correo: " + request.getEmail()));

        if (StringUtils.hasText(request.getPassword()) && StringUtils.hasText(user.getPasswordHash())) {
            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                throw new BadRequestException("Contraseña incorrecta");
            }
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

        return AuthResponseDto.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getFullName())
                .email(user.getEmail())
                .travelerLevel(user.getTravelerLevel())
                .build();
    }

    public AuthResponseDto loginWithGoogle(String idToken) {
        if (!StringUtils.hasText(idToken)) {
            throw new BadRequestException("El token de Google ID es obligatorio");
        }

        try {
            // Verificar el token con el endpoint oficial de Google OAuth2
            String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("email")) {
                throw new BadRequestException("Token de Google inválido");
            }

            String googleEmail = ((String) body.get("email")).toLowerCase().trim();
            String googleSub = (String) body.get("sub");
            String googleName = (String) body.getOrDefault("name", googleEmail.substring(0, googleEmail.indexOf('@')));

            AppUser user = userRepository.findByGoogleId(googleSub)
                    .or(() -> userRepository.findByEmail(googleEmail))
                    .orElseGet(() -> {
                        AppUser newUser = AppUser.builder()
                                .id("usr_" + UUID.randomUUID().toString().substring(0, 8))
                                .email(googleEmail)
                                .googleId(googleSub)
                                .fullName(googleName)
                                .originCity("Cajicá / Sabana Centro")
                                .favoriteCoffeeMethod("V60 Dripper")
                                .avatarEmoji("🌟")
                                .travelerLevel("Explorador Cittaslow")
                                .ecoPoints(200)
                                .build();
                        return userRepository.save(newUser);
                    });

            if (user.getGoogleId() == null) {
                user.setGoogleId(googleSub);
                userRepository.save(user);
            }

            String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

            return AuthResponseDto.builder()
                    .token(token)
                    .userId(user.getId())
                    .name(user.getFullName())
                    .email(user.getEmail())
                    .travelerLevel(user.getTravelerLevel())
                    .build();

        } catch (Exception ex) {
            log.error("Error validando token de Google: {}", ex.getMessage());
            throw new BadRequestException("No se pudo validar la sesión con Google: " + ex.getMessage());
        }
    }
}
