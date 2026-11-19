package com.example.androidsprints.data.database.entity

import androidx.room3.Entity

@Entity("ingredients")
data class IngredientsEntity(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String
)
