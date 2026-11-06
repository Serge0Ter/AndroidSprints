package com.example.androidsprints.features.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.androidsprints.R
import com.example.androidsprints.core.ui.EmptyMessage
import com.example.androidsprints.core.ui.ScreenHeader
import com.example.androidsprints.features.details.presentation.RecipeDetailsViewModel
import com.example.androidsprints.features.details.presentation.model.RecipeDetailsUiState
import com.example.androidsprints.ui.components.IngredientItem
import com.example.androidsprints.ui.components.PortionsSelector
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.theme.recipesAppTypography
import com.example.androidsprints.ui.utils.shareRecipe

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: RecipeDetailsViewModel = viewModel(
        factory = RecipeDetailsViewModel.provideFactory(recipeId)
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (val state = uiState) {
            RecipeDetailsUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )

            RecipeDetailsUiState.Error -> EmptyMessage(
                text = "Рецепт не найден",
                modifier = modifier.padding(Dimens.PaddingMain)
            )

            is RecipeDetailsUiState.Content -> {
                val recipe = state.recipe
                Column(
                    modifier = modifier
                        .padding(Dimens.PaddingMain)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    ScreenHeader(
                        imagePainter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(recipe.imageUrl)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.loading),
                            error = painterResource(R.drawable.error)
                        ),
                        contentDescription = recipe.title,
                        title = recipe.title,
                        showShareButton = true,
                        onShareClick = { shareRecipe(context, recipe.id, recipe.title) },
                        showFavoriteButton = true,
                        isFavorite = recipe.isFavorite,
                        onFavoriteToggle = { viewModel.toggleFavorite() }
                    )
                    Text(
                        modifier = Modifier.padding(vertical = Dimens.PaddingMedium),
                        text = stringResource(R.string.ingredients).uppercase(),
                        color = MaterialTheme.colorScheme.primary,
                        style = recipesAppTypography.displayLarge
                    )
                    PortionsSelector(
                        currentPortions = state.currentPortions,
                        onPortionsChange = { viewModel.updatePortions(it) }
                    )
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.RoundedCorner8))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        state.scaledIngredients.forEachIndexed { index, model ->
                            IngredientItem(model)
                            if (index != state.scaledIngredients.lastIndex) HorizontalDivider(
                                modifier = Modifier.padding(
                                    Dimens.PaddingMedium
                                )
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.padding(vertical = Dimens.PaddingMedium),
                        text = stringResource(R.string.cooking_method).uppercase(),
                        color = MaterialTheme.colorScheme.primary,
                        style = recipesAppTypography.displayLarge
                    )
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.RoundedCorner8))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        recipe.method.forEachIndexed { index, method ->
                            Text(
                                modifier = Modifier.padding(Dimens.PaddingMedium),
                                text = "${index + 1}. $method"
                            )
                            if (index != recipe.method.lastIndex) HorizontalDivider(
                                modifier = Modifier.padding(
                                    Dimens.PaddingMedium
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}