package com.munitour.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para crear o actualizar un plato típico.
 * Representa la gastronomía local de cada municipio.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("destination_id")
    private String destinationId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Long price;

    @JsonProperty("emoji")
    private String emoji;

    @JsonProperty("is_signature")
    private Boolean isSignature;

    @JsonProperty("wait_time_min")
    private Integer waitTimeMin;
}
