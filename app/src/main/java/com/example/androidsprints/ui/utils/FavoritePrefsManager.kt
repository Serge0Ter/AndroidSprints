package com.example.androidsprints.ui.utils

import android.content.Context
import androidx.core.content.edit

class FavoritePrefsManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("recipe_app_prefs", Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean = getAllFavorites().contains("$recipeId")


    fun addToFavorites(recipeId: Int) {
        val favorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val updateFavorite = favorites?.toMutableSet() ?: mutableSetOf()
        updateFavorite.add("$recipeId")
        sharedPreferences.edit {
            putStringSet("favorite_recipe_ids", updateFavorite)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val favorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val updateFavorite = favorites?.toMutableSet() ?: mutableSetOf()
        updateFavorite.remove("$recipeId")
        sharedPreferences.edit {
            putStringSet("favorite_recipe_ids", updateFavorite)
        }
    }

    fun getAllFavorites(): Set<String> =
        sharedPreferences.getStringSet("favorite_recipe_ids", emptySet()).orEmpty()

}
