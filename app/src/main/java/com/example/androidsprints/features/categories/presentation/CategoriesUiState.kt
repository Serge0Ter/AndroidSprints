package com.example.androidsprints.features.categories.presentation

import com.example.androidsprints.features.categories.presentation.model.CategoryUiModel

data class CategoriesUiState(
    val categories: List<CategoryUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
