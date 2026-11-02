package com.example.androidsprints.features.recipes.presentation.model

data class RecipesUiState(
    val recipes: List<RecipeUiModel> = emptyList(),
    val title: String = "",
    val image: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false
) {
    val isEmpty: Boolean
        get() = recipes.isEmpty()
}
