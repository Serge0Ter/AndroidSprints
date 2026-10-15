package com.example.androidsprints.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.androidsprints.R
import com.example.androidsprints.ScreenId
import com.example.androidsprints.data.repository.getRecipesByCategoryId
import com.example.androidsprints.ui.components.ScreenHeader
import com.example.androidsprints.ui.recipes.model.RecipeUiModel
import com.example.androidsprints.ui.recipes.model.toUiModel
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun RecipesScreen(
    categoryId: Int,
    categoryTitle: String,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    LaunchedEffect(categoryId) {
        recipes = getRecipesByCategoryId(categoryId).map { it.toUiModel() }
    }

    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.categories),
            contentDescription = ScreenId.RECIPES.title,
            title = categoryTitle
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Dimens.PaddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
        ) {
            items(recipes, key = { it.id }) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onClick = onRecipeClick
                )
            }
        }
    }
}