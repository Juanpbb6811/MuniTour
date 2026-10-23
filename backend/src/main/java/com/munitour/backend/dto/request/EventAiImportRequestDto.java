package com.munitour.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la ingesta inteligente de eventos desde redes sociales
 * (Instagram, Facebook, X o comunicados de la Alcaldía/Cultura).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventAiImportRequestDto {

    /**
     * Texto sin procesar de la publicación de Instagram/Facebook.
     */
    @JsonProperty("post_text")
    private String postText;

    /**
     * URL opcional de la imagen o flyer del evento.
     */
    @JsonProperty("image_url")
    private String imageUrl;

    /**
     * Enlace de la publicación original en redes sociales.
     */
    @JsonProperty("source_url")
    private String sourceUrl;

    /**
     * Destino municipal (por defecto "cajica").
     */
    @JsonProperty("destination_id")
    private String destinationId;
}
