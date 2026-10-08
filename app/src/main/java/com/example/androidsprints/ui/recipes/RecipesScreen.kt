package com.example.androidsprints.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.androidsprints.R
import com.example.androidsprints.ScreenId
import com.example.androidsprints.ui.components.ScreenHeader

@Composable
fun RecipesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.categories),
            contentDescription = ScreenId.RECIPES.title,
            title = "Скоро здесь будет список рецептов"
        )
    }
}