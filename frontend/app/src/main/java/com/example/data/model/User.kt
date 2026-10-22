package com.example.data.model

data class UserProfile(
    val id: String = "user_guest",
    val name: String = "Viajero Explorador",
    val email: String = "viajero@munitour.co",
    val phone: String = "+57 310 123 4567",
    val bio: String = "Amante de los senderos de Sabana Centro, la fotografía y el café de altura.",
    val avatarEmoji: String = "🧭",
    val originCity: String = "Cajicá / Bogotá",
    val travelerLevel: String = "Explorador Cittaslow",
    val preferredTransport: String = "Tren / RegioTram",
    val memberSince: String = "Octubre 2026",
    val completedRoutesCount: Int = 4,
    val visitedPlacesCount: Int = 11,
    val reviewsCount: Int = 6,
    val ecoPoints: Int = 420,
    val favoriteCoffeeMethod: String = "V60 Dripper",
    val isLoggedIn: Boolean = false,
    val token: String? = null
)
