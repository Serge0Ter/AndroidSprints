package com.example.androidsprints.app.di

import android.app.Application
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.favorites.presentation.FavoritesViewModel

class FavoritesViewModelFactory(
    private val application: Application,
    private val repository: RecipesRepository,
) : Factory<FavoritesViewModel> {
    override fun create(): FavoritesViewModel = FavoritesViewModel(
        application = application,
        repository = repository
    )
}