package com.example.androidsprints.features.details.presentation.model

import com.example.androidsprints.features.recipes.presentation.model.IngredientUiModel
import com.example.androidsprints.features.recipes.presentation.model.RecipeUiModel
import kotlin.math.roundToInt

sealed class RecipeDetailsUiState {
    object Loading : RecipeDetailsUiState()
    object Error : RecipeDetailsUiState()
    data class Content(val recipe: RecipeUiModel, val currentPortions: Int) :
        RecipeDetailsUiState() {
        val scaledIngredients: List<IngredientUiModel>
            get() {
                val multiplier = currentPortions.toDouble() / recipe.serving
                return recipe.ingredients.map {
                    val amount = it.quantity.toDoubleOrNull()
                    if (amount != null) {
                        val scaled = multiplier * amount
                        val rounded = (scaled * 100).roundToInt() / 100.0
                        val text =
                            if (rounded % 1 == 0.0) rounded.toInt().toString()
                            else rounded.toString()
                        it.copy(quantity = text)
                    } else it
                }
            }
    }
}