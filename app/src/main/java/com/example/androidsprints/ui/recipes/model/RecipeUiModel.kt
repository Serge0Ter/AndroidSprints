package com.example.androidsprints.ui.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.androidsprints.data.model.RecipeDto
import com.example.androidsprints.ui.components.Constants
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val serving: Int,
    val isFavorite: Boolean,
) : Parcelable

fun RecipeDto.toUiModel(isFavorite: Boolean = false) = RecipeUiModel(
    id = id,
    title = title,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    serving = serving,
    isFavorite = isFavorite
)
