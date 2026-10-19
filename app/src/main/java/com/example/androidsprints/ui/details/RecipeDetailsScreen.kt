package com.example.androidsprints.ui.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidsprints.ui.recipes.model.RecipeUiModel
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(recipe: RecipeUiModel, modifier: Modifier = Modifier) {
    Text(
        text = recipe.title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier.padding(Dimens.PaddingMain)
    )
}