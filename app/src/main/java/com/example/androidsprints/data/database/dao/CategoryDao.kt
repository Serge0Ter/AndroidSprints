package com.example.androidsprints.data.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.example.androidsprints.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}