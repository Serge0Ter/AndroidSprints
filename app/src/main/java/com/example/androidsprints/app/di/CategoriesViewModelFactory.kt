package com.example.androidsprints.app.di

import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.categories.presentation.CategoriesViewModel

class CategoriesViewModelFactory(private val repository: RecipesRepository) :
    Factory<CategoriesViewModel> {
    override fun create(): CategoriesViewModel = CategoriesViewModel(repository)
}
