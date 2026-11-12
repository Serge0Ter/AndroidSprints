package com.example.androidsprints.core.network.api

import com.example.androidsprints.data.model.CategoryDto
import com.example.androidsprints.data.model.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApiService {
    @GET("category")
    suspend fun getCategories(): List<CategoryDto>

    @GET("category/{id}")
    suspend fun getCategory(@Path("id") categoryId: Int): CategoryDto

    @GET("recipe/{id}")
    suspend fun getRecipes(@Path("id") recipeId: Int): RecipeDto

    @GET("category/{id}/recipes")
    suspend fun getRecipesByCategory(@Path("id") categoryId: Int): List<RecipeDto>
}