package com.example.androidsprints.data.repository

import com.example.androidsprints.core.network.api.RecipeApiService
import com.example.androidsprints.data.model.CategoryDto
import com.example.androidsprints.data.model.RecipeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepositoryImpl(private val apiService: RecipeApiService) : RecipesRepository {
    override suspend fun getCategories(): List<CategoryDto> {
        try {
            return withContext(Dispatchers.IO) { apiService.getCategories() }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto {
        try {
            return withContext(Dispatchers.IO) { apiService.getRecipes(recipeId) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto> {
        try {
            return withContext(Dispatchers.IO) { apiService.getRecipesByCategory(categoryId) }
        } catch (e: Exception) {
            throw e
        }
    }

}