package com.example.androidsprints.core.network

import com.example.androidsprints.BuildConfig
import com.example.androidsprints.core.network.api.RecipeApiService
import com.example.androidsprints.core.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object ApiClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(Constants.TIMEOUT_15, TimeUnit.SECONDS)  // 1. Установка соединения
        .readTimeout(Constants.TIMEOUT_30, TimeUnit.SECONDS)     // 2. Чтение ответа от сервера
        .writeTimeout(Constants.TIMEOUT_30, TimeUnit.SECONDS)    // 3. Отправка данных на сервер
        .build()
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val apiService = retrofit.create(RecipeApiService::class.java)
}