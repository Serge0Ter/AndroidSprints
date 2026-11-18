package com.example.androidsprints.data.database

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.androidsprints.data.database.dao.CategoryDao
import com.example.androidsprints.data.database.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    companion object {
        @Volatile
        private var instance: RecipesDatabase? = null

        fun getInstance(context: Context): RecipesDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipesDatabase::class.java,
                    "recipes_database.db"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
    }
}
