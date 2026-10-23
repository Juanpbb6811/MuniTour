package com.munitour.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa un lugar turístico o gastronómico en un municipio.
 * Los campos tipo lista se almacenan como cadenas separadas por comas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "places")
public class Place {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Builder.Default
    @Column(name = "destination_id")
    private String destinationId = "cajica";

    @Builder.Default
    @Column(name = "municipality")
    private String municipality = "Cajicá";

    /** Tipo de lugar: "cultura" o "sabor" */
    @Column(name = "kind", nullable = false)
    private String kind;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "emoji", nullable = false)
    private String emoji;

    @Builder.Default
    @Column(name = "stars")
    private Integer stars = 5;

    @Builder.Default
    @Column(name = "review_count")
    private Integer reviewCount = 0;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Builder.Default
    @Column(name = "x_ratio")
    private Float xRatio = 0.5f;

    @Builder.Default
    @Column(name = "y_ratio")
    private Float yRatio = 0.5f;

    @Column(name = "address")
    private String address;

    @Builder.Default
    @Column(name = "distance_km")
    private Float distanceKm = 0.8f;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    /** Métodos de preparación de café, almacenados como texto separado por comas */
    @Column(name = "coffee_methods", columnDefinition = "TEXT")
    private String coffeeMethods;

    @Column(name = "coffee_origin")
    private String coffeeOrigin;

    @Column(name = "barista_specialty")
    private String baristaSpecialty;

    @Column(name = "table_service_dynamics")
    private String tableServiceDynamics;

    @Column(name = "estimated_wait_minutes")
    private String estimatedWaitMinutes;

    @Column(name = "business_hours")
    private String businessHours;

    @Builder.Default
    @Column(name = "price_tier")
    private String priceTier = "$$";

    /** Métodos de pago aceptados, almacenados como texto separado por comas */
    @Column(name = "payment_methods", columnDefinition = "TEXT")
    private String paymentMethods;

    @Builder.Default
    @Column(name = "has_wifi")
    private Boolean hasWifi = true;

    @Builder.Default
    @Column(name = "is_pet_friendly")
    private Boolean isPetFriendly = true;

    @Builder.Default
    @Column(name = "outdoor_terrace")
    private Boolean outdoorTerrace = true;

    /** Historia del dueño o fundador del lugar */
    @Column(name = "owner_story", columnDefinition = "TEXT")
    private String ownerStory;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
