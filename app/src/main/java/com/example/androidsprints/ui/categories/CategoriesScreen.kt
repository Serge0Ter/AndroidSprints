package com.example.androidsprints.ui.categories

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.androidsprints.R
import com.example.androidsprints.ScreenId
import com.example.androidsprints.ui.components.ScreenHeader

@Composable
fun CategoriesScreen() {
    ScreenHeader(
        imagePainter = painterResource(id = R.drawable.categories),
        contentDescription = ScreenId.CATEGORIES.title,
        title = ScreenId.CATEGORIES.title,
    )
}