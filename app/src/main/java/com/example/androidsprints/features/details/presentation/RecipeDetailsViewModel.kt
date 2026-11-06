package com.example.androidsprints.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.details.presentation.model.RecipeDetailsUiState
import com.example.androidsprints.features.recipes.presentation.model.toUiModel
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    private val recipeId: Int
) : AndroidViewModel(application) {
    companion object {
        fun provideFactory(recipeId: Int): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RecipeDetailsViewModel(
                    application = this[APPLICATION_KEY] as Application,
                    recipeId = recipeId
                )
            }
        }
    }

    private val favoriteManager = FavoriteDataStoreManager(application)
    private val recipe = RecipesRepository().getRecipeById(recipeId)?.toUiModel()
    private val portion = MutableStateFlow(recipe?.serving?.coerceIn(1, 12) ?: 1)

    val uiState: StateFlow<RecipeDetailsUiState> = recipe?.let { r ->
        combine(portion, favoriteManager.isFavoriteFlow(r.id)) { currentPortion, isFavorite ->
            RecipeDetailsUiState.Content(
                recipe = r.copy(isFavorite = isFavorite),
                currentPortions = currentPortion
            )
        }
    }?.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RecipeDetailsUiState.Loading
    ) ?: MutableStateFlow(RecipeDetailsUiState.Error)

    fun updatePortions(count: Int) {
        portion.value = count.coerceIn(1, 12)
    }

    fun toggleFavorite() = viewModelScope.launch {
        val id = recipe?.id ?: return@launch
        if (favoriteManager.isFavorite(id)) favoriteManager.removeFavorite(id)
        else favoriteManager.addFavorite(id)
    }

}