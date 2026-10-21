package com.munitour.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa una reseña de un lugar turístico o gastronómico.
 * Incluye calificación general y calificación específica de café.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "place_id")
    private String placeId;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "date")
    private String date;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "service_highlight")
    private String serviceHighlight;

    @Builder.Default
    @Column(name = "coffee_rating")
    private Integer coffeeRating = 5;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
