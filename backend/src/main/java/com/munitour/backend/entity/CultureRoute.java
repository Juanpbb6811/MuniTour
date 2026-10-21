package com.munitour.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa una ruta cultural dentro de un municipio.
 * Incluye información de audio-guía patrimonial para recorridos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "culture_routes")
public class CultureRoute {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "destination_id")
    private String destinationId;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "duration_text")
    private String durationText;

    @Column(name = "distance_text")
    private String distanceText;

    @Column(name = "difficulty")
    private String difficulty;

    /** Lugares incluidos en la ruta, almacenados como texto separado por comas */
    @Column(name = "places_included", columnDefinition = "TEXT")
    private String placesIncluded;

    @Column(name = "audio_title")
    private String audioTitle;

    @Builder.Default
    @Column(name = "audio_duration_sec")
    private Integer audioDurationSec = 185;

    @Builder.Default
    @Column(name = "audio_narrator")
    private String audioNarrator = "Guía Patrimonial Local";

    @Column(name = "audio_transcript", columnDefinition = "TEXT")
    private String audioTranscript;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
