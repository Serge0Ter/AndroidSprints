package com.example.androidsprints.ui.recipes.model

import com.example.androidsprints.data.model.IngredientDto

data class IngredientUiModel(val name: String, val quantity: String, val unitOfMeasure: String)

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = description,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
)