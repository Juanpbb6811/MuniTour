package com.munitour.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa un usuario de la aplicación MuniTour.
 * Soporta autenticación local (email/password) y Google OAuth.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "email", nullable = false, unique = true, length = 128)
    private String email;

    /** Nullable para usuarios que se registran con Google OAuth */
    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;

    @Builder.Default
    @Column(name = "origin_city")
    private String originCity = "Cajicá, Cundinamarca";

    @Builder.Default
    @Column(name = "phone", length = 32)
    private String phone = "+57 310 123 4567";

    @Builder.Default
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio = "Apasionado por la gastronomía, el senderismo y las tradiciones de Sabana Centro.";

    @Builder.Default
    @Column(name = "preferred_transport", length = 64)
    private String preferredTransport = "Tren / RegioTram";

    @Builder.Default
    @Column(name = "favorite_coffee_method")
    private String favoriteCoffeeMethod = "Filtrado Chemex";

    @Builder.Default
    @Column(name = "avatar_emoji")
    private String avatarEmoji = "🧭";

    @Builder.Default
    @Column(name = "eco_points")
    private Integer ecoPoints = 0;

    /** ID de Google para OAuth — nullable y único */
    @Column(name = "google_id", unique = true)
    private String googleId;

    @Builder.Default
    @Column(name = "traveler_level")
    private String travelerLevel = "Explorador Cittaslow";

    @Builder.Default
    @Column(name = "completed_routes_count")
    private Integer completedRoutesCount = 0;

    @Builder.Default
    @Column(name = "visited_places_count")
    private Integer visitedPlacesCount = 0;

    @Builder.Default
    @Column(name = "reviews_count")
    private Integer reviewsCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
