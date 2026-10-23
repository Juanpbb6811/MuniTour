package com.example.data.model

data class OperativeDetailsService(
    val coffeeMethods: List<String> = emptyList(),
    val coffeeOrigin: String = "",
    val baristaSpecialty: String = "",
    val tableServiceDynamics: String = "Servicio a la mesa completo",
    val estimatedWaitMinutes: String = "10 - 15 min",
    val businessHours: String = "Lun - Dom: 7:00 a.m. - 8:30 p.m.",
    val priceTier: String = "$$",
    val paymentMethods: List<String> = listOf("Efectivo", "Nequi", "Daviplata", "Tarjetas"),
    val hasWifi: Boolean = true,
    val isPetFriendly: Boolean = true,
    val outdoorTerrace: Boolean = true,
    val ownerStory: String = ""
)

data class Place(
    val id: String,
    val destinationId: String = "cajica",
    val municipality: String = "Cajicá",
    val kind: String, // "cultura" or "sabor"
    val name: String,
    val tag: String,
    val emoji: String,
    val stars: Int = 5,
    val reviewCount: Int = 48,
    val latitude: Double = 4.9186,
    val longitude: Double = -74.0259,
    val xRatio: Float = 0.5f,
    val yRatio: Float = 0.5f,
    val address: String = "",
    val distanceKm: Float = 0.8f,
    val imageUrl: String? = null,
    val operative: OperativeDetailsService = OperativeDetailsService()
)
