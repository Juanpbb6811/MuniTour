package com.example.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DestinationDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "region") val region: String,
    @Json(name = "country") val country: String,
    @Json(name = "flag") val flag: String,
    @Json(name = "currency_symbol") val currencySymbol: String,
    @Json(name = "currency_code") val currencyCode: String,
    @Json(name = "tag_line") val tagLine: String,
    @Json(name = "description") val description: String
)

@JsonClass(generateAdapter = true)
data class OperativeDetailsDto(
    @Json(name = "coffee_methods") val coffeeMethods: List<String> = emptyList(),
    @Json(name = "coffee_origin") val coffeeOrigin: String = "",
    @Json(name = "barista_specialty") val baristaSpecialty: String = "",
    @Json(name = "table_service_dynamics") val tableServiceDynamics: String = "",
    @Json(name = "estimated_wait_minutes") val estimatedWaitMinutes: String = "",
    @Json(name = "business_hours") val businessHours: String = "",
    @Json(name = "price_tier") val priceTier: String = "$$",
    @Json(name = "payment_methods") val paymentMethods: List<String> = emptyList(),
    @Json(name = "has_wifi") val hasWifi: Boolean = true,
    @Json(name = "is_pet_friendly") val isPetFriendly: Boolean = true,
    @Json(name = "outdoor_terrace") val outdoorTerrace: Boolean = true,
    @Json(name = "owner_story") val ownerStory: String = ""
)

@JsonClass(generateAdapter = true)
data class PlaceDto(
    @Json(name = "id") val id: String,
    @Json(name = "destination_id") val destinationId: String = "cajica",
    @Json(name = "municipality") val municipality: String = "Cajicá",
    @Json(name = "kind") val kind: String,
    @Json(name = "name") val name: String,
    @Json(name = "tag") val tag: String,
    @Json(name = "emoji") val emoji: String,
    @Json(name = "stars") val stars: Int = 5,
    @Json(name = "review_count") val reviewCount: Int = 0,
    @Json(name = "latitude") val latitude: Double = 4.9186,
    @Json(name = "longitude") val longitude: Double = -74.0259,
    @Json(name = "x_ratio") val xRatio: Float = 0.5f,
    @Json(name = "y_ratio") val yRatio: Float = 0.5f,
    @Json(name = "address") val address: String = "",
    @Json(name = "distance_km") val distanceKm: Float = 0.8f,
    @Json(name = "operative") val operative: OperativeDetailsDto = OperativeDetailsDto()
)

@JsonClass(generateAdapter = true)
data class DishDto(
    @Json(name = "id") val id: String,
    @Json(name = "destination_id") val destinationId: String = "cajica",
    @Json(name = "name") val name: String,
    @Json(name = "category") val category: String,
    @Json(name = "place_id") val placeId: String,
    @Json(name = "place_name") val placeName: String,
    @Json(name = "description") val description: String,
    @Json(name = "price") val price: Long,
    @Json(name = "emoji") val emoji: String,
    @Json(name = "is_signature") val isSignature: Boolean = false,
    @Json(name = "wait_time_min") val waitTimeMin: Int = 10
)

@JsonClass(generateAdapter = true)
data class CulturalEventDto(
    @Json(name = "id") val id: String,
    @Json(name = "destination_id") val destinationId: String = "cajica",
    @Json(name = "day") val day: Int,
    @Json(name = "day_name") val dayName: String,
    @Json(name = "month_year") val monthYear: String = "Septiembre 2026",
    @Json(name = "title") val title: String,
    @Json(name = "time") val time: String,
    @Json(name = "duration") val duration: String,
    @Json(name = "place") val place: String,
    @Json(name = "is_free") val isFree: Boolean = true,
    @Json(name = "emoji") val emoji: String = "📅",
    @Json(name = "category") val category: String = "Cultura",
    @Json(name = "description") val description: String = ""
)

@JsonClass(generateAdapter = true)
data class InterMunicipalRouteDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "subtitle") val subtitle: String,
    @Json(name = "theme") val theme: String,
    @Json(name = "municipalities") val municipalities: List<String>,
    @Json(name = "total_distance_km") val totalDistanceKm: Double,
    @Json(name = "estimated_duration") val estimatedDuration: String,
    @Json(name = "transport_mode") val transportMode: String,
    @Json(name = "difficulty") val difficulty: String,
    @Json(name = "key_stops") val keyStops: List<String>,
    @Json(name = "description") val description: String,
    @Json(name = "highlight_tip") val highlightTip: String,
    @Json(name = "emoji") val emoji: String = "🛤️"
)

@JsonClass(generateAdapter = true)
data class ReviewDto(
    @Json(name = "id") val id: String,
    @Json(name = "place_id") val placeId: String,
    @Json(name = "author_name") val authorName: String,
    @Json(name = "rating") val rating: Int,
    @Json(name = "date") val date: String,
    @Json(name = "comment") val comment: String,
    @Json(name = "service_highlight") val serviceHighlight: String = "",
    @Json(name = "coffee_rating") val coffeeRating: Int = 5
)

@JsonClass(generateAdapter = true)
data class AuthRequestDto(
    @Json(name = "email") val email: String,
    @Json(name = "name") val name: String? = null,
    @Json(name = "password") val password: String? = null
)

@JsonClass(generateAdapter = true)
data class AuthResponseDto(
    @Json(name = "token") val token: String,
    @Json(name = "user_id") val userId: String,
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "traveler_level") val travelerLevel: String = "Explorador Cittaslow"
)
