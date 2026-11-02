package com.example.androidsprints.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.androidsprints.R
import com.example.androidsprints.core.ui.EmptyMessage
import com.example.androidsprints.core.ui.ErrorMessage
import com.example.androidsprints.core.ui.ScreenHeader
import com.example.androidsprints.features.recipes.RecipesViewModel
import com.example.androidsprints.features.recipes.presentation.model.RecipeUiModel
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun RecipesScreen(
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: RecipesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.image)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = stringResource(R.string.recipes),
            title = uiState.title
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                uiState.isError -> ErrorMessage(
                    message = stringResource(R.string.errorMessageRecipe),
                    onRetry = viewModel::loadRecipe,
                    modifier = Modifier.align(Alignment.Center)
                )

                uiState.isEmpty -> EmptyMessage(
                    text = stringResource(R.string.recipes_empty),
                    modifier = Modifier.align(Alignment.Center),
                )

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(Dimens.PaddingMain),
                        verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                    ) {
                        items(uiState.recipes, key = { it.id }) { recipe ->
                            RecipeItem(
                                recipe = recipe,
                                onClick = { onRecipeClick(recipe.id, recipe) }
                            )
                        }
                    }
                }
            }
        }
    }
}