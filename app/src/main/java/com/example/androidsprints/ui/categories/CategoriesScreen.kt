package com.example.androidsprints.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.androidsprints.R
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.ui.categories.model.toUiModel
import com.example.androidsprints.ui.components.ScreenHeader
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    repository: RecipesRepository,
    modifier: Modifier = Modifier,
    onCategoryClick: (Int) -> Unit,
) {
    val categories = remember { repository.getCategories().map { it.toUiModel() } }
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.categories),
            contentDescription = stringResource(R.string.categories),
            title = stringResource(R.string.categories),
        )
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(Dimens.PaddingMain),
            horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMain),
        ) {
            items(categories, key = { category -> category.id }) { category ->
                CategoryItem(
                    category = category,
                    onClick = { onCategoryClick(category.id) },
                )
            }

        }
    }
}