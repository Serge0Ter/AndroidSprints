package com.example.androidsprints.features.details.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.androidsprints.core.utils.NavArgs
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.details.presentation.model.RecipeDetailsUiState
import com.example.androidsprints.features.recipes.presentation.model.toUiModel
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow<RecipeDetailsUiState>(RecipeDetailsUiState.Loading)
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        val recipeId = savedStateHandle.get<Int>(NavArgs.RECIPE_ID)
        if (recipeId == null) {
            _uiState.value = RecipeDetailsUiState.Error
        } else {
            loadRecipe(recipeId)
        }
    }

    fun updatePortions(count: Int) {
        val state = _uiState.value
        if (state is RecipeDetailsUiState.Content) _uiState.value = state.copy(
            currentPortions = count.coerceIn(
                Dimens.minCountSlider,
                Dimens.maxCountSlider
            )
        )
    }

    fun toggleFavorite() = viewModelScope.launch {
        val state = _uiState.value
        if (state !is RecipeDetailsUiState.Content) return@launch
        val id = state.recipe.id
        if (state.recipe.isFavorite) favoriteManager.removeFavorite(id)
        else favoriteManager.addFavorite(id)
    }

    private fun loadRecipe(recipeId: Int) {
        observeFavorite(recipeId)
        viewModelScope.launch {
            try {
                repository.getRecipe(recipeId)
                    .catch {
                        Log.e("RecipeDetailsViewModel", "loadRecipe: ${it.message}", it)
                        _uiState.value = RecipeDetailsUiState.Error
                    }.collect { entity ->
                        if (entity != null) {
                            _uiState.value = RecipeDetailsUiState.Content(
                                recipe = entity.toUiModel(),
                                currentPortions = entity.serving.coerceIn(
                                    Dimens.minCountSlider, Dimens.maxCountSlider
                                )
                            )
                        }
                    }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("RecipeDetailsViewModel", "Не удалось загрузить рецепт", e)
                _uiState.value = RecipeDetailsUiState.Error
            }
        }
    }

    private fun observeFavorite(recipeId: Int) {
        viewModelScope.launch {
            favoriteManager.isFavoriteFlow(recipeId).collect { isFavorite ->
                _uiState.update { state ->
                    if (state is RecipeDetailsUiState.Content) {
                        state.copy(recipe = state.recipe.copy(isFavorite = isFavorite))
                    } else state
                }
            }
        }
    }
}
