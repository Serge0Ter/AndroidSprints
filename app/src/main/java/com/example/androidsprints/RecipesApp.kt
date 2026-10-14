package com.example.androidsprints

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsprints.ui.categories.CategoriesScreen
import com.example.androidsprints.ui.favorites.FavoritesScreen
import com.example.androidsprints.ui.navigation.BottomNavigation
import com.example.androidsprints.ui.recipes.RecipesScreen
import com.example.androidsprints.ui.theme.RecipesAppTheme

@Composable
fun RecipesApp() {
    RecipesAppTheme {
        var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
        var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
        var selectedCategoryTitle by remember { mutableStateOf("") }
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
                        CategoriesScreen(
                            modifier = Modifier.padding(innerPadding),
                            onCategoryClick = { categoryId, categoryTitle ->
                                selectedCategoryId = categoryId
                                selectedCategoryTitle = categoryTitle
                                currentScreen = ScreenId.RECIPES
                            }
                        )
                    }

                    ScreenId.FAVORITES -> {
                        FavoritesScreen(modifier = Modifier.padding(innerPadding))
                    }

                    ScreenId.RECIPES -> {
                        RecipesScreen(
                            categoryId = selectedCategoryId ?: error("Category ID is required"),
                            categoryTitle = selectedCategoryTitle
                        )
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
