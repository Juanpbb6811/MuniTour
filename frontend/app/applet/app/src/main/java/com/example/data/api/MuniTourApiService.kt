package com.example.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MuniTourApiService {

    @GET("destinations")
    suspend fun getDestinations(): List<DestinationDto>

    @GET("places")
    suspend fun getPlaces(
        @Query("destinationId") destinationId: String? = null,
        @Query("kind") kind: String? = null
    ): List<PlaceDto>

    @POST("places/self-manage")
    suspend fun registerPlace(
        @Body place: PlaceDto
    ): PlaceDto

    @GET("dishes")
    suspend fun getDishes(
        @Query("destinationId") destinationId: String? = null,
        @Query("category") category: String? = null
    ): List<DishDto>

    @GET("events")
    suspend fun getEvents(
        @Query("destinationId") destinationId: String? = null,
        @Query("day") day: Int? = null
    ): List<CulturalEventDto>

    @GET("routes/inter-municipal")
    suspend fun getInterMunicipalRoutes(): List<InterMunicipalRouteDto>

    @GET("reviews")
    suspend fun getReviews(
        @Query("placeId") placeId: String? = null
    ): List<ReviewDto>

    @POST("reviews")
    suspend fun submitReview(
        @Body review: ReviewDto
    ): ReviewDto

    @POST("auth/login")
    suspend fun login(
        @Body req: AuthRequestDto
    ): AuthResponseDto

    @POST("auth/register")
    suspend fun register(
        @Body req: AuthRequestDto
    ): AuthResponseDto
}
