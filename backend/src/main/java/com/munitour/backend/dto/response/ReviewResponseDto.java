package com.munitour.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para una reseña de un lugar.
 * Estructura exacta esperada por el frontend Android (ReviewDto).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

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
