package com.example.androidsprints.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.androidsprints.core.utils.NavArgs
import com.example.androidsprints.data.repository.RecipesRepositoryImpl
import com.example.androidsprints.features.details.presentation.model.RecipeDetailsUiState
import com.example.androidsprints.features.recipes.presentation.model.toUiModel
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepositoryImpl,
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow<RecipeDetailsUiState>(RecipeDetailsUiState.Loading)
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()
//    val uiState: StateFlow<RecipeDetailsUiState> = recipe
//        ?.let { r ->
//            combine(portion, favoriteManager.isFavoriteFlow(r.id)) { currentPortion, isFavorite ->
//                RecipeDetailsUiState.Content(
//                    recipe = r.copy(isFavorite = isFavorite),
//                    currentPortions = currentPortion
//                )
//            }
//        }?.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = RecipeDetailsUiState.Loading
//        ) ?: MutableStateFlow(RecipeDetailsUiState.Error)

    init {
        val recipeId = savedStateHandle.get<Int>(NavArgs.RECIPE_ID)
        if (recipeId == null) {
            _uiState.value = RecipeDetailsUiState.Error
        } else {
            loadRecipe(recipeId)
            observeFavorite(recipeId)
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
        viewModelScope.launch {
            val loaded = repository.getRecipe(recipeId).toUiModel()
            _uiState.value = RecipeDetailsUiState.Content(
                recipe = loaded, currentPortions = loaded.serving.coerceIn(
                    Dimens.minCountSlider, Dimens.maxCountSlider
                )
            )
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