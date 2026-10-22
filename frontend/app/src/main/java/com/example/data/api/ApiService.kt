package com.example.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit REST Service definition for MuniTour.
 * Connects directly to the Java Spring Boot Backend hosted on Render with Supabase PostgreSQL.
 */
interface ApiService {

    @GET("api/places")
    suspend fun getPlaces(
        @Query("destinationId") destinationId: String? = null,
        @Query("municipality") municipality: String? = null
    ): List<PlaceDto>

    @POST("api/places")
    suspend fun createPlace(@Body place: PlaceDto): PlaceDto

    @GET("api/dishes")
    suspend fun getDishes(
        @Query("destinationId") destinationId: String? = null
    ): List<DishDto>

    @POST("api/dishes")
    suspend fun createDish(@Body dish: DishDto): DishDto

    @GET("api/events")
    suspend fun getEvents(
        @Query("destinationId") destinationId: String? = null,
        @Query("day") day: Int? = null
    ): List<CulturalEventDto>

    @POST("api/events")
    suspend fun createEvent(@Body event: CulturalEventDto): CulturalEventDto

    @GET("api/routes")
    suspend fun getRoutes(
        @Query("destinationId") destinationId: String? = null
    ): List<CultureRouteDto>

    @GET("api/reviews/{placeId}")
    suspend fun getReviewsForPlace(@Path("placeId") placeId: String): List<ReviewDto>

    @POST("api/reviews")
    suspend fun submitReview(@Body review: ReviewDto): ReviewDto

    @POST("api/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

    @POST("api/auth/google")
    suspend fun googleLogin(@Body request: GoogleAuthRequest): AuthResponse

    @GET("api/routes/inter-municipal")
    suspend fun getInterMunicipalRoutes(): List<InterMunicipalRouteDto>

    @GET("api/users/me")
    suspend fun getCurrentUser(): UserProfileDto

    @PUT("api/users/me")
    suspend fun updateCurrentUser(@Body request: UserUpdateProfileDto): UserProfileDto
}
