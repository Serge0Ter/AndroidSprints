package com.example.androidsprints.features.favorites.presentation

import android.app.Application
import android.util.Log
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipesRepository()
    private val favoriteManager = FavoriteDataStoreManager(application)
    private val _retryTrigger = MutableStateFlow(0)
    val uiState: StateFlow<FavoritesUiState> = _retryTrigger.flatMapLatest {
        favoriteManager.getFavoriteIdsFlow()
            .map { ids ->
                ids.mapNotNull { rawId ->
                    rawId.toIntOrNull()?.let { repository.getRecipeById(it)?.toUiModel() }
                }
            }
            .map { recipes ->
                if (recipes.isEmpty()) FavoritesUiState.Empty else FavoritesUiState.Content(recipes)
            }
            .onStart {
                emit(FavoritesUiState.Loading)
            }
            .catch { e ->
                Log.e("FavoritesViewModel", "favorites flow failed", e)
                emit(FavoritesUiState.Error)
            }
    }.stateIn(
        scope = viewModelScope,
        initialValue = FavoritesUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun retry() = _retryTrigger.update { it + 1 }
}