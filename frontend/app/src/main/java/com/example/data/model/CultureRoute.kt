package com.example.data.model

data class CultureRoute(
    val id: String,
    val destinationId: String = "cajica",
    val title: String,
    val subtitle: String,
    val durationText: String,
    val distanceText: String,
    val difficulty: String,
    val placesIncluded: List<String>,
    val audioTitle: String,
    val audioDurationSec: Int = 185,
    val audioNarrator: String = "Guía Patrimonial Local",
    val audioTranscript: String = ""
)
