package com.munitour.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para actualizar el perfil del usuario.
 * Permite modificar nombre, ciudad de origen, avatar y método de café favorito.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("origin_city")
    private String originCity;

    @JsonProperty("avatar_emoji")
    private String avatarEmoji;

    @JsonProperty("favorite_coffee_method")
    private String favoriteCoffeeMethod;

    @JsonProperty("preferred_transport")
    private String preferredTransport;
}
