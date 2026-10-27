package com.example.androidsprints.ui.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first

class FavoriteDataStoreManager(context: Context) {
    private val dataStore = context.dataStore

    suspend fun isFavorite(recipeId: Int): Boolean {
        val preferences = dataStore.data.first()
        val favoriteId = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        return favoriteId.contains(recipeId.toString())
    }

    suspend fun addFavorite(recipeId: Int) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updateFavorites = currentFavorites + recipeId.toString()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updateFavorites
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updateFavorites = currentFavorites - recipeId.toString()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updateFavorites
        }
    }
}