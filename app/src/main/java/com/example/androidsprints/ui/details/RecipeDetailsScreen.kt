package com.example.androidsprints.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.androidsprints.R
import com.example.androidsprints.data.repository.getRecipeById
import com.example.androidsprints.ui.components.IngredientItem
import com.example.androidsprints.ui.components.PortionsSelector
import com.example.androidsprints.ui.components.ScreenHeader
import com.example.androidsprints.ui.recipes.model.toUiModel
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.theme.recipesAppTypography
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager
import com.example.androidsprints.ui.utils.shareRecipe
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(recipeId: Int, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val favoriteDataStoreManager = remember { FavoriteDataStoreManager(context.applicationContext) }
    val recipe = getRecipeById(recipeId)?.toUiModel()
    if (recipe != null) {
        val isFavorite by remember(recipeId) {
            favoriteDataStoreManager
                .isFavoriteFlow(recipe.id)
        }.collectAsState(initial = false)
        var currentPortions by rememberSaveable { mutableIntStateOf(recipe.serving) }
        val scaledIngredients = remember(currentPortions) {
            val multiplier = currentPortions.toDouble() / recipe.serving
            recipe.ingredients.map {
                val amount = it.quantity.toDoubleOrNull()
                if (amount != null) {
                    val scaled = multiplier * amount
                    val rounded = (scaled * 100).roundToInt() / 100.0
                    val text =
                        if (rounded % 1 == 0.0) rounded.toInt().toString() else rounded.toString()
                    it.copy(quantity = text)
                } else it
            }
        }
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
                isFavorite = isFavorite,
                onFavoriteToggle = {
                    coroutineScope.launch {
                        if (isFavorite) favoriteDataStoreManager.removeFavorite(recipe.id)
                        else favoriteDataStoreManager.addFavorite(recipe.id)
                    }

                }
            )
            Text(
                modifier = Modifier.padding(vertical = Dimens.PaddingMedium),
                text = stringResource(R.string.ingredients).uppercase(),
                color = MaterialTheme.colorScheme.primary,
                style = recipesAppTypography.displayLarge
            )
            PortionsSelector(
                currentPortions = currentPortions,
                onPortionsChange = { currentPortions = it }
            )
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.RoundedCorner8))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                scaledIngredients.forEachIndexed { index, model ->
                    IngredientItem(model)
                    if (index != scaledIngredients.lastIndex) HorizontalDivider(
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
    } else {
        Text(
            text = "Рецепт не найден",
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier.padding(Dimens.PaddingMain)
        )
    }
}