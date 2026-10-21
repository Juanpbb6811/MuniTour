package com.munitour.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para crear o actualizar una reseña de un lugar.
 * Los viajeros pueden dejar sus opiniones y calificaciones.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("date")
    private String date;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("service_highlight")
    private String serviceHighlight;

    @JsonProperty("coffee_rating")
    private Integer coffeeRating;
}
