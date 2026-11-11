package com.example.androidsprints.data.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiClient {
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }
}