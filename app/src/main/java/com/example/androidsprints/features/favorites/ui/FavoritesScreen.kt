package com.example.androidsprints.features.favorites.ui

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidsprints.R
import com.example.androidsprints.core.ui.EmptyMessage
import com.example.androidsprints.core.ui.ErrorMessage
import com.example.androidsprints.core.ui.ScreenHeader
import com.example.androidsprints.features.favorites.presentation.model.FavoritesUiState
import com.example.androidsprints.features.favorites.presentation.model.FavoritesViewModel
import com.example.androidsprints.features.recipes.ui.RecipeItem
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun FavoritesScreen(
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: FavoritesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.bcg_favorites),
            contentDescription = stringResource(R.string.favorites),
            title = stringResource(R.string.favorites),
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is FavoritesUiState.Content -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(Dimens.PaddingMain),
                        verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                    ) {
                        items(
                            state.favoriteRecipes,
                            key = { it.id }) { recipe ->
                            RecipeItem(
                                recipe = recipe,
                                onClick = onRecipeClick,
                            )
                        }
                    }
                }

                FavoritesUiState.Empty -> EmptyMessage(
                    text = "Вы еще не добавили ни одного рецепта в избранное",
                    modifier = Modifier.align(Alignment.Center),
                )

                FavoritesUiState.Error -> ErrorMessage(
                    message = stringResource(R.string.errorMessageRecipe),
                    onRetry = { },
                    modifier = Modifier.align(Alignment.Center)
                )

                FavoritesUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}