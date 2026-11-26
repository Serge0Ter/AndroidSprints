package com.example.androidsprints.app.di

import android.content.Context
import com.example.androidsprints.core.network.ApiClient
import com.example.androidsprints.data.database.RecipesDatabase
import com.example.androidsprints.data.repository.RecipesRepositoryImpl

class AppContainer(context: Context) {
    val apiClient = ApiClient.apiService
    val recipesDatabase = RecipesDatabase.buildDatabase(context = context)
    val recipesRepository = RecipesRepositoryImpl(
        apiService = apiClient,
        database = recipesDatabase
    )
}
