package com.example.androidsprints.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.androidsprints.R
import com.example.androidsprints.core.ui.EmptyMessage
import com.example.androidsprints.core.ui.ErrorMessage
import com.example.androidsprints.core.ui.ScreenHeader
import com.example.androidsprints.features.categories.presentation.CategoriesUiState
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    uiState: CategoriesUiState,
    modifier: Modifier = Modifier,
    onCategoryClick: (Int) -> Unit,
    onRetry: () -> Unit
) {
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.categories),
            contentDescription = stringResource(R.string.categories),
            title = stringResource(R.string.categories),
        )
        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            when {
                uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                uiState.errorMessage != null -> ErrorMessage(
                    message = uiState.errorMessage,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center)
                )

                uiState.categories.isEmpty() ->
                    EmptyMessage(
                        text = stringResource(R.string.categories_empty),
                        modifier = Modifier.align(Alignment.Center),
                    )

                else -> LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(Dimens.PaddingMain),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMain),
                    verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMain),
                ) {
                    items(uiState.categories, key = { category -> category.id }) { category ->
                        CategoryItem(
                            category = category,
                            onClick = { onCategoryClick(category.id) })
                    }
                }
            }
        }
    }
}