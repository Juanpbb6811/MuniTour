package com.munitour.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para un lugar turístico.
 * Estructura exacta esperada por el frontend Android (PlaceDto).
 * Incluye detalles operativos como objeto anidado.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("destination_id")
    private String destinationId;

    @JsonProperty("municipality")
    private String municipality;

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("emoji")
    private String emoji;

    @JsonProperty("stars")
    private Double stars;

    @JsonProperty("review_count")
    private Integer reviewCount;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("x_ratio")
    private Double xRatio;

    @JsonProperty("y_ratio")
    private Double yRatio;

    @JsonProperty("address")
    private String address;

    @JsonProperty("distance_km")
    private Double distanceKm;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("operative")
    private OperativeDetailsResponseDto operative;
}
