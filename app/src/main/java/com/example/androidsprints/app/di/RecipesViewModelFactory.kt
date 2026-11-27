package com.example.androidsprints.app.di

import androidx.lifecycle.SavedStateHandle
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.recipes.presentation.RecipesViewModel

class RecipesViewModelFactory(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) : Factory<RecipesViewModel> {
    override fun create(): RecipesViewModel = RecipesViewModel(
        savedStateHandle = savedStateHandle,
        repository = repository
    )
}