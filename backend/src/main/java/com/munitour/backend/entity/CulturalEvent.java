package com.munitour.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa un evento cultural programado en un municipio.
 * Incluye información de día, horario, lugar y categoría del evento.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cultural_events")
public class CulturalEvent {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "destination_id")
    private String destinationId;

    @Column(name = "day")
    private Integer day;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "month_year")
    private String monthYear;

    @Column(name = "title")
    private String title;

    @Column(name = "time")
    private String time;

    @Column(name = "duration")
    private String duration;

    @Column(name = "place")
    private String place;

    @Builder.Default
    @Column(name = "is_free")
    private Boolean isFree = true;

    @Column(name = "emoji")
    private String emoji;

    @Column(name = "category")
    private String category;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
