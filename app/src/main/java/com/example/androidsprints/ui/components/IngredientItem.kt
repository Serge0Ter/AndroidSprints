package com.example.androidsprints.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidsprints.ui.recipes.model.IngredientUiModel

@Composable
fun IngredientItem(ingredient: IngredientUiModel, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            modifier = Modifier.weight(1f),
            text = ingredient.name.uppercase()
        )
        Text(
            text = ingredient.quantity
        )
        Text(
            text = ingredient.unitOfMeasure.uppercase()
        )
    }
}