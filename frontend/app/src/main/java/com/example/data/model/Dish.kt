package com.example.data.model

data class Dish(
    val id: String,
    val destinationId: String = "cajica",
    val name: String,
    val category: String, // "tradicional", "cafe", "parrilla", "rapida", "reposteria"
    val placeId: String,
    val placeName: String,
    val description: String,
    val price: Long,
    val emoji: String,
    val isSignature: Boolean = false,
    val waitTimeMin: Int = 12
)
