package com.example.androidsprints.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.example.androidsprints.data.model.IngredientDto

@Immutable
data class IngredientUiModel(val name: String, val quantity: String, val unitOfMeasure: String)

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = description,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
)