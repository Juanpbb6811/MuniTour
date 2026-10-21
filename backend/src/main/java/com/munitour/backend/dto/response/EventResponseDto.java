package com.munitour.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para un evento cultural.
 * Estructura exacta esperada por el frontend Android (CulturalEventDto).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("destination_id")
    private String destinationId;

    @JsonProperty("day")
    private Integer day;

    @JsonProperty("day_name")
    private String dayName;

    @JsonProperty("month_year")
    private String monthYear;

    @JsonProperty("title")
    private String title;

    @JsonProperty("time")
    private String time;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("place")
    private String place;

    @JsonProperty("is_free")
    private Boolean isFree;

    @JsonProperty("emoji")
    private String emoji;

    @JsonProperty("category")
    private String category;

    @JsonProperty("description")
    private String description;
}
