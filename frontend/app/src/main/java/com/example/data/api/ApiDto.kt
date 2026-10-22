package com.example.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OperativeDetailsDto(
    @Json(name = "coffeeMethods") val coffeeMethods: List<String> = emptyList(),
    @Json(name = "coffeeOrigin") val coffeeOrigin: String = "",
    @Json(name = "baristaSpecialty") val baristaSpecialty: String = "",
    @Json(name = "tableServiceDynamics") val tableServiceDynamics: String = "Servicio a la mesa completo",
    @Json(name = "estimatedWaitMinutes") val estimatedWaitMinutes: String = "10 - 15 min",
    @Json(name = "businessHours") val businessHours: String = "Lun - Dom: 7:00 a.m. - 8:30 p.m.",
    @Json(name = "priceTier") val priceTier: String = "$$",
    @Json(name = "paymentMethods") val paymentMethods: List<String> = listOf("Efectivo", "Nequi", "Daviplata", "Tarjetas"),
    @Json(name = "hasWifi") val hasWifi: Boolean = true,
    @Json(name = "isPetFriendly") val isPetFriendly: Boolean = true,
    @Json(name = "outdoorTerrace") val outdoorTerrace: Boolean = true,
    @Json(name = "ownerStory") val ownerStory: String = ""
)

@JsonClass(generateAdapter = true)
data class PlaceDto(
    @Json(name = "id") val id: String,
    @Json(name = "destinationId") val destinationId: String = "cajica",
    @Json(name = "municipality") val municipality: String = "Cajicá",
    @Json(name = "kind") val kind: String, // "comercio", "turismo", "cultura"
    @Json(name = "name") val name: String,
    @Json(name = "tag") val tag: String,
    @Json(name = "emoji") val emoji: String,
    @Json(name = "stars") val stars: Int = 5,
    @Json(name = "reviewCount") val reviewCount: Int = 48,
    @Json(name = "latitude") val latitude: Double = 4.9186,
    @Json(name = "longitude") val longitude: Double = -74.0259,
    @Json(name = "xRatio") val xRatio: Float = 0.5f,
    @Json(name = "yRatio") val yRatio: Float = 0.5f,
    @Json(name = "address") val address: String = "",
    @Json(name = "distanceKm") val distanceKm: Float = 0.8f,
    @Json(name = "operative") val operative: OperativeDetailsDto = OperativeDetailsDto()
)

@JsonClass(generateAdapter = true)
data class DishDto(
    @Json(name = "id") val id: String,
    @Json(name = "destinationId") val destinationId: String = "cajica",
    @Json(name = "name") val name: String,
    @Json(name = "category") val category: String = "tradicional",
    @Json(name = "placeId") val placeId: String,
    @Json(name = "placeName") val placeName: String,
    @Json(name = "description") val description: String,
    @Json(name = "price") val price: Long = 18000L,
    @Json(name = "emoji") val emoji: String = "🍲",
    @Json(name = "isSignature") val isSignature: Boolean = false,
    @Json(name = "waitTimeMin") val waitTimeMin: Int = 12
)

@JsonClass(generateAdapter = true)
data class CulturalEventDto(
    @Json(name = "id") val id: String,
    @Json(name = "destinationId") val destinationId: String = "cajica",
    @Json(name = "day") val day: Int,
    @Json(name = "dayName") val dayName: String,
    @Json(name = "monthYear") val monthYear: String = "Septiembre 2026",
    @Json(name = "title") val title: String,
    @Json(name = "time") val time: String,
    @Json(name = "duration") val duration: String,
    @Json(name = "place") val place: String,
    @Json(name = "isFree") val isFree: Boolean = true,
    @Json(name = "emoji") val emoji: String = "🎭",
    @Json(name = "category") val category: String = "cultural",
    @Json(name = "description") val description: String = ""
)

@JsonClass(generateAdapter = true)
data class CultureRouteDto(
    @Json(name = "id") val id: String,
    @Json(name = "destinationId") val destinationId: String = "cajica",
    @Json(name = "title") val title: String,
    @Json(name = "subtitle") val subtitle: String,
    @Json(name = "durationText") val durationText: String,
    @Json(name = "distanceText") val distanceText: String,
    @Json(name = "difficulty") val difficulty: String = "Fácil",
    @Json(name = "audioDurationSec") val audioDurationSec: Int = 180,
    @Json(name = "audioTitle") val audioTitle: String = "",
    @Json(name = "narrationExcerpt") val narrationExcerpt: String = "",
    @Json(name = "stops") val stops: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ReviewDto(
    @Json(name = "id") val id: String,
    @Json(name = "placeId") val placeId: String,
    @Json(name = "authorName") val authorName: String,
    @Json(name = "rating") val rating: Int,
    @Json(name = "date") val date: String,
    @Json(name = "comment") val comment: String,
    @Json(name = "serviceHighlight") val serviceHighlight: String = "Excelente servicio",
    @Json(name = "coffeeRating") val coffeeRating: Int = 5
)

@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "fullName") val fullName: String? = null
)

@JsonClass(generateAdapter = true)
data class GoogleAuthRequest(
    @Json(name = "id_token") val idToken: String
)

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "token") val token: String = "",
    @Json(name = "user_id") val userId: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "email") val email: String = "",
    @Json(name = "traveler_level") val travelerLevel: String = "Explorador Cittaslow",
    @Json(name = "role") val role: String = "traveler"
)

@JsonClass(generateAdapter = true)
data class InterMunicipalRouteDto(
    @Json(name = "id") val id: String = "",
    @Json(name = "title") val title: String = "",
    @Json(name = "subtitle") val subtitle: String = "",
    @Json(name = "theme") val theme: String = "",
    @Json(name = "municipalities") val municipalities: List<String> = emptyList(),
    @Json(name = "total_distance_km") val totalDistanceKm: Double = 0.0,
    @Json(name = "estimated_duration") val estimatedDuration: String = "",
    @Json(name = "transport_mode") val transportMode: String = "",
    @Json(name = "difficulty") val difficulty: String = "",
    @Json(name = "key_stops") val keyStops: List<String> = emptyList(),
    @Json(name = "description") val description: String = "",
    @Json(name = "highlight_tip") val highlightTip: String = "",
    @Json(name = "emoji") val emoji: String = "🛤️"
)

@JsonClass(generateAdapter = true)
data class UserProfileDto(
    @Json(name = "id") val id: String = "",
    @Json(name = "email") val email: String = "",
    @Json(name = "full_name") val fullName: String = "",
    @Json(name = "phone") val phone: String? = null,
    @Json(name = "bio") val bio: String? = null,
    @Json(name = "origin_city") val originCity: String? = null,
    @Json(name = "avatar_emoji") val avatarEmoji: String? = null,
    @Json(name = "traveler_level") val travelerLevel: String? = null,
    @Json(name = "favorite_coffee_method") val favoriteCoffeeMethod: String? = null,
    @Json(name = "preferred_transport") val preferredTransport: String? = null,
    @Json(name = "completed_routes_count") val completedRoutesCount: Int = 0,
    @Json(name = "visited_places_count") val visitedPlacesCount: Int = 0,
    @Json(name = "reviews_count") val reviewsCount: Int = 0,
    @Json(name = "eco_points") val ecoPoints: Int = 0,
    @Json(name = "created_at") val createdAt: String? = null
)

@JsonClass(generateAdapter = true)
data class UserUpdateProfileDto(
    @Json(name = "name") val name: String? = null,
    @Json(name = "phone") val phone: String? = null,
    @Json(name = "bio") val bio: String? = null,
    @Json(name = "origin_city") val originCity: String? = null,
    @Json(name = "avatar_emoji") val avatarEmoji: String? = null,
    @Json(name = "favorite_coffee_method") val favoriteCoffeeMethod: String? = null,
    @Json(name = "preferred_transport") val preferredTransport: String? = null
)
