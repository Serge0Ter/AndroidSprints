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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)
    private val userPortions = MutableStateFlow<Int?>(null)
    private val recipeId: Int? = savedStateHandle.get<Int>(NavArgs.RECIPE_ID)
    val uiState: StateFlow<RecipeDetailsUiState> = recipeId?.let { id ->
        combine(
            repository.getRecipe(id),
            favoriteManager.isFavoriteFlow(id),
            userPortions
        ) { dto, isFavorite, portions ->
            if (dto == null) {
                RecipeDetailsUiState.Loading
            } else {
                RecipeDetailsUiState.Content(
                    recipe = dto.toUiModel().copy(isFavorite = isFavorite),
                    currentPortions = portions ?: dto.serving.coerceIn(
                        Dimens.minCountSlider, Dimens.maxCountSlider
                    )
                )
            }
        }
            .catch {
                Log.e("RecipeDetailsViewModel", "uiState: ${it.message}", it)
                emit(RecipeDetailsUiState.Error)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                RecipeDetailsUiState.Loading
            )
    } ?: MutableStateFlow(RecipeDetailsUiState.Error)

    fun updatePortions(count: Int) {
        userPortions.value = count.coerceIn(Dimens.minCountSlider, Dimens.maxCountSlider)
    }

    fun toggleFavorite() = viewModelScope.launch {
        val state = uiState.value as? RecipeDetailsUiState.Content ?: return@launch
        if (state.recipe.isFavorite) favoriteManager.removeFavorite(state.recipe.id)
        else favoriteManager.addFavorite(state.recipe.id)
    }
}
