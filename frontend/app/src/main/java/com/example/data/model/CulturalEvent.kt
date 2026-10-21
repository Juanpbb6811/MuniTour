package com.example.data.model

data class CulturalEvent(
    val id: String,
    val destinationId: String = "cajica",
    val day: Int,
    val dayName: String,
    val monthYear: String = "Septiembre 2026",
    val title: String,
    val time: String,
    val duration: String,
    val place: String,
    val isFree: Boolean,
    val emoji: String,
    val category: String = "Cultura",
    val description: String = ""
)
