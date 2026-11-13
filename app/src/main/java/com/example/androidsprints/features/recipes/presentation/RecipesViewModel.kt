package com.example.androidsprints.features.recipes.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprints.core.utils.NavArgs
import com.example.androidsprints.data.repository.RecipesRepositoryImpl
import com.example.androidsprints.features.recipes.presentation.model.RecipesUiState
import com.example.androidsprints.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class RecipesViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepositoryImpl
) : ViewModel() {
    private val categoryId: Int = checkNotNull(savedStateHandle[NavArgs.CATEGORY_ID])
    private val categoryTitle: String = URLDecoder.decode(
        savedStateHandle[NavArgs.CATEGORY_TITLE] ?: "",
        StandardCharsets.UTF_8.toString()
    )
    private val categoryImage: String = URLDecoder.decode(
        savedStateHandle[NavArgs.CATEGORY_IMAGE_URL] ?: "",
        StandardCharsets.UTF_8.toString()
    )
    private val _uiState = MutableStateFlow(
        RecipesUiState(
            title = categoryTitle,
            image = categoryImage,
            isLoading = true
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadRecipe()
    }

    fun loadRecipe() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }
            try {
                val recipes = repository.getRecipesByCategory(categoryId).map { it.toUiModel() }
                _uiState.update {
                    it.copy(
                        recipes = recipes,
                        isLoading = false
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("RecipesViewModel", "loadRecipes failed", e)
                _uiState.update {
                    it.copy(isLoading = false, isError = true)
                }
            }
        }

    }
}