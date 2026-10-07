package com.example.androidsprints

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsprints.ui.categories.CategoriesScreen
import com.example.androidsprints.ui.navigation.BottomNavigation
import com.example.androidsprints.ui.theme.RecipesAppTheme

@Composable
fun RecipesApp() {
    RecipesAppTheme {
        var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { currentScreen = ScreenId.CATEGORIES },
                    onFavoriteClick = { currentScreen = ScreenId.FAVORITES }
                )
            },
            content = { innerPadding ->
                when (currentScreen) {
                    ScreenId.CATEGORIES -> {
                        CategoriesScreen()
                    }

                    ScreenId.FAVORITES -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center,
                            content = {
                                Text(
                                    text = ScreenId.FAVORITES.title
                                )
                            })
                    }
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreviews() {
    RecipesApp()
}
