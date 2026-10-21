package com.example.data.model

data class InterMunicipalRoute(
    val id: String,
    val title: String,
    val subtitle: String,
    val theme: String, // "Ferroviaria", "Ecoturismo", "Gastronomía", "Cicloturismo", "Arqueología"
    val municipalities: List<String>,
    val totalDistanceKm: Double,
    val estimatedDuration: String,
    val transportMode: String, // "Tren / RegioTram", "Bicicleta MTB", "Caminata", "Vehículo"
    val difficulty: String,
    val keyStops: List<String>,
    val description: String,
    val highlightTip: String,
    val emoji: String = "🛤️"
)
