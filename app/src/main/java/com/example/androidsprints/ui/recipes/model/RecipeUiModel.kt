package com.example.androidsprints.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.example.androidsprints.data.model.IngredientDto
import com.example.androidsprints.data.model.RecipeDto
import com.example.androidsprints.ui.components.Constants

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val isFavorite: Boolean,
)

fun RecipeDto.toUiModel(isFavorite: Boolean = false) = RecipeUiModel(
    id = id,
    title = title,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    isFavorite = isFavorite
)
