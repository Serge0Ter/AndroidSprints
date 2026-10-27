package com.example.androidsprints

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.androidsprints.ui.navigation.BottomNavigation
import com.example.androidsprints.ui.navigation.Destination
import com.example.androidsprints.ui.navigation.RecipesNavHost
import com.example.androidsprints.ui.navigation.navigateToTab
import com.example.androidsprints.ui.theme.RecipesAppTheme
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager

@Composable
fun RecipesApp(
    deepLinkIntent: Intent?,
    onDeepLink: () -> Unit
) {
    RecipesAppTheme {
        val navController = rememberNavController()
        val context = LocalContext.current
        val favoriteDataStoreManager = remember {
            FavoriteDataStoreManager(context.applicationContext)
        }
        val favoriteCount by remember {
            favoriteDataStoreManager
                .getFavoriteCountFlow()
        }
            .collectAsState(initial = 0)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { navController.navigateToTab(Destination.Categories) },
                    onFavoriteClick = { navController.navigateToTab(Destination.Favorites) },
                    favoriteCount = favoriteCount
                )
            },
            content = { innerPadding ->
                RecipesNavHost(
                    deepLink = deepLinkIntent,
                    onDeepLink = onDeepLink,
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            })
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreviews() {
    RecipesApp(
        deepLinkIntent = null,
        onDeepLink = {}
    )
}
