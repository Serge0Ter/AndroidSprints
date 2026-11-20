package com.example.androidsprints.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.favorites.presentation.model.FavoritesUiState
import com.example.androidsprints.features.recipes.presentation.model.toUiModel
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModel(
    application: Application,
    private val repository: RecipesRepository
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)
    private val _retryTrigger = MutableStateFlow(0)
    val uiState: StateFlow<FavoritesUiState> = _retryTrigger.flatMapLatest {
        favoriteManager.getFavoriteIdsFlow()
            .map { rawIds -> rawIds.mapNotNull { it.toIntOrNull() } }
            .flatMapLatest { ids ->
                if (ids.isEmpty()) flowOf(emptyList())
                else repository.getRecipesByIds(ids)
            }
            .map { dtos -> dtos.map { it.toUiModel() } }
            .map { uiModels ->
                if (uiModels.isEmpty()) FavoritesUiState.Empty else FavoritesUiState.Content(
                    uiModels
                )
            }
    }.stateIn(
        scope = viewModelScope,
        initialValue = FavoritesUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun retry() = _retryTrigger.update { it + 1 }
}