package com.example.androidsprints.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidsprints.ui.recipes.model.IngredientUiModel
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun IngredientItem(ingredient: IngredientUiModel, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(Dimens.PaddingMedium)) {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Text(
                modifier = Modifier.weight(1f),
                text = ingredient.name.uppercase()
            )
            Text(
                text = ingredient.quantity.uppercase()
            )
            Text(
                text = ingredient.unitOfMeasure.uppercase()
            )
        }
    }

}