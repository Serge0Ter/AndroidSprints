package com.example.androidsprints.data.model

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(val quantity: String, val unitOfMeasure: String, val description: String)
