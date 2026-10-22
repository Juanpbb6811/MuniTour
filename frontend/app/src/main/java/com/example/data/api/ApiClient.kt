package com.example.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton API Client configured for connecting to the Java Spring Boot backend on Render
 * and Supabase. Supports dynamic URL reconfiguration for local development in IntelliJ (e.g. 10.0.2.2:8080)
 * and automatic Bearer JWT Token attachment.
 */
object ApiClient {
    // Default URL pointing to the cloud service on Render
    private var currentBaseUrl = "https://munitour.onrender.com/"

    @Volatile
    private var authToken: String? = null

    fun setAuthToken(token: String?) {
        authToken = token
    }

    fun getAuthToken(): String? = authToken

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()
        authToken?.let { token ->
            if (token.isNotBlank()) {
                builder.header("Authorization", "Bearer $token")
            }
        }
        chain.proceed(builder.build())
    }

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
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

    fun updateBaseUrl(newUrl: String) {
        if (newUrl.isNotBlank() && newUrl != currentBaseUrl) {
            currentBaseUrl = newUrl
            retrofitInstance = buildRetrofit(newUrl)
            apiService = retrofitInstance.create(ApiService::class.java)
        }
    }

    fun getBaseUrl(): String = currentBaseUrl
}
