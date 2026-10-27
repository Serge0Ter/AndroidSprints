package com.example.androidsprints.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.androidsprints.R
import com.example.androidsprints.data.repository.getRecipeById
import com.example.androidsprints.ui.components.ScreenHeader
import com.example.androidsprints.ui.recipes.RecipeItem
import com.example.androidsprints.ui.recipes.model.toUiModel
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager
import kotlinx.coroutines.flow.map

@Composable
fun FavoritesScreen(
    favoriteDataStoreManager: FavoriteDataStoreManager,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val favorites = remember(favoriteDataStoreManager) {
        favoriteDataStoreManager.getFavoriteIdsFlow().map { ids ->
            ids.mapNotNull { rawId ->
                rawId.toIntOrNull()?.let(::getRecipeById)?.toUiModel()
            }
        }
    }.collectAsState(emptyList())
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.bcg_favorites),
            contentDescription = stringResource(R.string.favorites),
            title = stringResource(R.string.favorites),
        )
        if (favorites.value.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.PaddingMain),
                verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
            ) {
                items(favorites.value, key = { it.id }) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onClick = onRecipeClick,
                    )
                }
            }
        } else {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "Вы еще не добавили ни одного рецепта в избранное"
            )
        }
    }
}