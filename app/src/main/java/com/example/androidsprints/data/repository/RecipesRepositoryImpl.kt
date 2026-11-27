package com.example.androidsprints.data.repository

import android.util.Log
import com.example.androidsprints.core.network.api.RecipeApiService
import com.example.androidsprints.data.database.RecipesDatabase
import com.example.androidsprints.data.model.CategoryDto
import com.example.androidsprints.data.model.RecipeDto
import com.example.androidsprints.data.model.toDto
import com.example.androidsprints.data.model.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecipesRepositoryImpl(
    private val apiService: RecipeApiService,
    database: RecipesDatabase,
    private val externalScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
) : RecipesRepository {

    private val categoryDao = database.categoryDao()
    private val recipeDao = database.recipesDao()

    override fun getCategories(): Flow<List<CategoryDto>> {
        externalScope.launch {
            try {
                val fresh = apiService.getCategories()
                categoryDao.insertCategories(fresh.map { it.toEntity() })
            } catch (e: Exception) {
                Log.e("RecipesRepositoryImpl", "Ошибка обновления категории: ${e.message}")
            }
        }
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDto() }
        }
    }

    override fun getRecipesByIds(ids: List<Int>): Flow<List<RecipeDto>> {
        return recipeDao.getRecipesByIds(ids).map { recipes ->
            recipes.map { it.toDto() }
        }
    }

    override fun getRecipe(recipeId: Int): Flow<RecipeDto?> {
        externalScope.launch {
            try {
                val fresh = apiService.getRecipes(recipeId)
                Log.d("RecipesRepositoryImpl", "Детали рецепта получены из API")
                val cached = recipeDao.getRecipe(recipeId).first()
                if (cached != null) {
                    recipeDao.insertRecipes(listOf(fresh.toEntity(cached.categoryId)))
                } else {
                    Log.w("RecipesRepositoryImpl", "Рецепт $recipeId отсутствует в кеше — категория неизвестна, вставка пропущена")
                }
            } catch (e: Exception) {
                Log.e("RecipesRepositoryImpl", "Ошибка обновления: ${e.message}")
            }
        }
        return recipeDao.getRecipe(recipeId).map { entity -> entity?.toDto() }
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        externalScope.launch {
            try {
                val fresh = apiService.getRecipesByCategory(categoryId)
                recipeDao.insertRecipes(fresh.map { it.toEntity(categoryId) })
            } catch (e: Exception) {
                Log.e("RecipesRepositoryImpl", "Ошибка обновления рецепта: ${e.message}")
            }
        }
        return recipeDao.getRecipesByCategory(categoryId)
            .map { entities -> entities.map { it.toDto() } }
    }
}