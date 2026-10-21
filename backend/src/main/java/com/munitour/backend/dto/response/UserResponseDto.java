package com.munitour.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta con el perfil completo del usuario.
 * Incluye estadísticas de viaje, puntos eco y preferencias.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("origin_city")
    private String originCity;

    @JsonProperty("avatar_emoji")
    private String avatarEmoji;

    @JsonProperty("traveler_level")
    private String travelerLevel;

    @JsonProperty("completed_routes_count")
    private Integer completedRoutesCount;

    @JsonProperty("visited_places_count")
    private Integer visitedPlacesCount;

    @JsonProperty("reviews_count")
    private Integer reviewsCount;

    @JsonProperty("eco_points")
    private Integer ecoPoints;

    @JsonProperty("favorite_coffee_method")
    private String favoriteCoffeeMethod;
}
