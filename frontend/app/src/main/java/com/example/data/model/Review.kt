package com.example.data.model

data class Review(
    val id: String,
    val placeId: String,
    val authorName: String,
    val rating: Int,
    val date: String,
    val comment: String,
    val serviceHighlight: String = "Excelente atención a la mesa",
    val coffeeRating: Int = 5
)
