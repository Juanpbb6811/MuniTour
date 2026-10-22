package com.munitour.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para autenticación exitosa.
 * Retorna el token JWT y datos básicos del usuario autenticado.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    @JsonProperty("token")
    private String token;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("userId")
    public String getCamelUserId() {
        return userId;
    }

    @JsonProperty("role")
    @Builder.Default
    private String role = "traveler";

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("traveler_level")
    private String travelerLevel;
}
