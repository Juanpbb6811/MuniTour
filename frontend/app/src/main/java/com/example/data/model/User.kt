package com.example.data.model

data class UserProfile(
    val id: String = "user_guest",
    val name: String = "Viajero Explorador",
    val email: String = "viajero@munitour.co",
    val avatarEmoji: String = "🧭",
    val originCity: String = "Bogotá, Colombia",
    val travelerLevel: String = "Explorador Cittaslow",
    val completedRoutesCount: Int = 3,
    val visitedPlacesCount: Int = 7,
    val reviewsCount: Int = 4,
    val ecoPoints: Int = 380,
    val favoriteCoffeeMethod: String = "V60 Dripper",
    val isLoggedIn: Boolean = false
)
