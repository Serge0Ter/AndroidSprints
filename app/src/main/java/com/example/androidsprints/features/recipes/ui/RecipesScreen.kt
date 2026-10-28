package com.example.androidsprints.features.recipes.ui

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
import androidx.compose.ui.res.stringResource
import com.example.androidsprints.R
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.recipes.presentation.model.RecipeUiModel
import com.example.androidsprints.features.recipes.presentation.model.toUiModel
import com.example.androidsprints.core.ui.ScreenHeader
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun RecipesScreen(
    categoryId: Int,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    repository: RecipesRepository,
    modifier: Modifier = Modifier
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    val title =
        remember(categoryId) {
            repository.getCategories().firstOrNull { categoryId == it.id }?.title.orEmpty()
        }
    LaunchedEffect(categoryId) {
        recipes = repository.getRecipesByCategoryId(categoryId).map { it.toUiModel() }
    }

    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.categories),
            contentDescription = stringResource(R.string.recipes),
            title = title
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Dimens.PaddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
        ) {
            items(recipes, key = { it.id }) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id, recipe) }
                )
            }
        }
    }
}