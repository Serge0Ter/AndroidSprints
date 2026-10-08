package com.example.androidsprints.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.androidsprints.R
import com.example.androidsprints.ScreenId
import com.example.androidsprints.ui.recipes.RecipesScreen
import com.example.androidsprints.ui.components.ScreenHeader

@Composable
fun FavoritesScreen(modifier: Modifier) {
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.bcg_favorites),
            contentDescription = ScreenId.FAVORITES.title,
            title = ScreenId.FAVORITES.title,
        )
    }
}