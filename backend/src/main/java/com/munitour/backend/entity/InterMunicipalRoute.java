package com.munitour.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa una ruta intermunicipal que conecta varios municipios.
 * Incluye información de transporte, dificultad y paradas clave.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inter_municipal_routes")
public class InterMunicipalRoute {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "theme")
    private String theme;

    /** Municipios de la ruta, almacenados como texto separado por comas */
    @Column(name = "municipalities")
    private String municipalities;

    @Column(name = "total_distance_km")
    private Double totalDistanceKm;

    @Column(name = "estimated_duration")
    private String estimatedDuration;

    @Column(name = "transport_mode")
    private String transportMode;

    @Column(name = "difficulty")
    private String difficulty;

    /** Paradas clave de la ruta, almacenadas como texto separado por comas */
    @Column(name = "key_stops")
    private String keyStops;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "highlight_tip")
    private String highlightTip;

    @Builder.Default
    @Column(name = "emoji")
    private String emoji = "🛤️";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
