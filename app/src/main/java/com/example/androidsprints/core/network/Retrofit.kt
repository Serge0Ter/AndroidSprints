package com.example.androidsprints.core.network

import com.example.androidsprints.core.network.api.RecipeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object Retrofit {

    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val apiService = retrofit.create(RecipeApiService::class.java)
}