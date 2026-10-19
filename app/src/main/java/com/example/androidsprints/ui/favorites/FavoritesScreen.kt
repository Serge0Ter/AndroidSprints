package com.example.androidsprints.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.androidsprints.R
import com.example.androidsprints.ui.components.ScreenHeader

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.bcg_favorites),
            contentDescription = stringResource(R.string.favorites),
            title = stringResource(R.string.favorites),
        )
    }
}