package com.example.androidsprints.data.repository

import com.example.androidsprints.data.model.CategoryDto
import com.example.androidsprints.data.model.RecipeDto
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    fun getCategories(): Flow<List<CategoryDto>>

    fun getRecipesByIds(ids: List<Int>): Flow<List<RecipeDto>>

    suspend fun getRecipe(recipeId: Int): RecipeDto

    fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>>
}