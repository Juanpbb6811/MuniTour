package com.munitour.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta para una ruta cultural dentro de un municipio.
 * Estructura exacta esperada por el frontend Android (CultureRouteDto).
 * Incluye información de audio guía para la ruta.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("destination_id")
    private String destinationId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("duration_text")
    private String durationText;

    @JsonProperty("distance_text")
    private String distanceText;

    @JsonProperty("difficulty")
    private String difficulty;

    @JsonProperty("places_included")
    private List<String> placesIncluded;

    @JsonProperty("audio_title")
    private String audioTitle;

    @JsonProperty("audio_duration_sec")
    private Integer audioDurationSec;

    @JsonProperty("audio_narrator")
    private String audioNarrator;

    @JsonProperty("audio_transcript")
    private String audioTranscript;
}
