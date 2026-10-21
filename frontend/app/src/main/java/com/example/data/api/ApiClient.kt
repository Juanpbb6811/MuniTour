package com.example.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton API Client configured for connecting to the Java Spring Boot backend on Render
 * and Supabase. Supports dynamic URL reconfiguration for local development in IntelliJ (e.g. 10.0.2.2:8080).
 */
object ApiClient {
    // Default URL pointing to the cloud service on Render
    private var currentBaseUrl = "https://munitour-backend.onrender.com/"

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Volatile
    private var retrofitInstance: Retrofit = buildRetrofit(currentBaseUrl)

    @Volatile
    var apiService: ApiService = retrofitInstance.create(ApiService::class.java)
        private set

    private fun buildRetrofit(url: String): Retrofit {
        val formattedUrl = if (url.endsWith("/")) url else "$url/"
        return Retrofit.Builder()
            .baseUrl(formattedUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Allows setting a custom backend URL (for instance when running locally via IntelliJ: http://10.0.2.2:8080/
     * or a custom deployed Render instance)
     */
    fun updateBaseUrl(newUrl: String) {
        if (newUrl.isNotBlank() && newUrl != currentBaseUrl) {
            currentBaseUrl = newUrl
            retrofitInstance = buildRetrofit(newUrl)
            apiService = retrofitInstance.create(ApiService::class.java)
        }
    }

    fun getBaseUrl(): String = currentBaseUrl
}
