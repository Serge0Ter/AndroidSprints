package com.example.androidsprints.data.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.example.androidsprints.data.database.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes ORDER BY title")
    fun getAllRecipes(): Flow<List<RecipeEntity>>
}