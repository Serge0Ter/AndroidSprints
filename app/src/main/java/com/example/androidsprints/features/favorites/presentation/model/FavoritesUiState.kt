package com.example.androidsprints.features.favorites.presentation.model

import com.example.androidsprints.features.recipes.presentation.model.RecipeUiModel

sealed class FavoritesUiState {
    data class Content(val favoriteRecipes: List<RecipeUiModel>) : FavoritesUiState()
    object Empty : FavoritesUiState()
    object Error : FavoritesUiState()
    object Loading : FavoritesUiState()
}
