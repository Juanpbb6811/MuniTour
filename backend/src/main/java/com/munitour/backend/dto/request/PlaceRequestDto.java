package com.munitour.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para crear o actualizar un lugar turístico.
 * Contiene toda la información del lugar incluyendo detalles operativos anidados.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequestDto {

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

    @JsonProperty("operative")
    private OperativeDetailsRequestDto operative;
}
