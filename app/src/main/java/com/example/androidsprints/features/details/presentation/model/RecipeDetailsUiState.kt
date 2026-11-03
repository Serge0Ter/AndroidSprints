package com.example.androidsprints.features.details.presentation.model

import com.example.androidsprints.features.recipes.presentation.model.RecipeUiModel

data class RecipeDetailsUiState(
    val recipes: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
