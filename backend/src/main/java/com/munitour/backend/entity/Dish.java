package com.munitour.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa un plato o producto gastronómico.
 * Categorías válidas: "tradicional", "cafe", "parrilla", "rapida", "reposteria".
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Builder.Default
    @Column(name = "destination_id")
    private String destinationId = "cajica";

    @Column(name = "name", nullable = false)
    private String name;

    /** Categoría del plato: tradicional, cafe, parrilla, rapida, reposteria */
    @Column(name = "category", nullable = false)
    private String category;

    /** Referencia conceptual al lugar — no se usa @ManyToOne para mantener simplicidad */
    @Column(name = "place_id")
    private String placeId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private Long price;

    @Column(name = "emoji")
    private String emoji;

    @Builder.Default
    @Column(name = "is_signature")
    private Boolean isSignature = false;

    @Builder.Default
    @Column(name = "wait_time_min")
    private Integer waitTimeMin = 15;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
