package com.example.androidsprints.data.repository

import com.example.androidsprints.data.model.CategoryDto
import com.example.androidsprints.data.model.RecipeDto

interface RecipesRepository {
    suspend fun getCategories(): List<CategoryDto>

//    suspend fun getCategory(categoryId: Int): CategoryDto

    suspend fun getRecipe(recipeId: Int): RecipeDto

    suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto>
}