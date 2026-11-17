package com.example.androidsprints.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.example.androidsprints.core.ui.ImageNetwork
import com.example.androidsprints.features.recipes.presentation.model.RecipeUiModel
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.theme.recipesAppTypography

@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    recipe: RecipeUiModel,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.RoundedCorner8),
        onClick = { onClick(recipe.id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.PaddingSmall
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.PaddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
        ) {
            ImageNetwork(
                imageURL = recipe.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = recipe.title.uppercase(),
                style = recipesAppTypography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
