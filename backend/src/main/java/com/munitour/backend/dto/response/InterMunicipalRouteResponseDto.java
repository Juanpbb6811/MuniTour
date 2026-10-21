package com.munitour.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta para una ruta intermunicipal.
 * Estructura exacta esperada por el frontend Android (InterMunicipalRouteDto).
 * Conecta múltiples municipios en una experiencia temática.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterMunicipalRouteResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("theme")
    private String theme;

    @JsonProperty("municipalities")
    private List<String> municipalities;

    @JsonProperty("total_distance_km")
    private Double totalDistanceKm;

    @JsonProperty("estimated_duration")
    private String estimatedDuration;

    @JsonProperty("transport_mode")
    private String transportMode;

    @JsonProperty("difficulty")
    private String difficulty;

    @JsonProperty("key_stops")
    private List<String> keyStops;

    @JsonProperty("description")
    private String description;

    @JsonProperty("highlight_tip")
    private String highlightTip;

    @JsonProperty("emoji")
    private String emoji;
}
