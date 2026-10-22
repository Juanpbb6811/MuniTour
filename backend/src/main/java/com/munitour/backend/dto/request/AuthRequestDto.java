package com.munitour.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para autenticación de usuario.
 * Soporta login con email/password y registro con nombre adicional.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fullName")
    public void setFullName(String fullName) {
        if (this.name == null || this.name.isBlank()) {
            this.name = fullName;
        }
    }

    @JsonProperty("password")
    private String password;
}
