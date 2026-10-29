package com.example.androidsprints.app.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.categories.presentation.CategoriesViewModel
import com.example.androidsprints.features.categories.ui.CategoriesScreen
import com.example.androidsprints.features.details.ui.RecipeDetailsScreen
import com.example.androidsprints.features.favorites.ui.FavoritesScreen
import com.example.androidsprints.features.recipes.ui.RecipesScreen
import com.example.androidsprints.ui.utils.FavoriteDataStoreManager
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RecipesNavHost(
    repository: RecipesRepository,
    favoriteDataStoreManager: FavoriteDataStoreManager,
    deepLink: Intent? = null,
    onDeepLink: () -> Unit = {},
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: CategoriesViewModel = viewModel(
        factory = viewModelFactory { initializer { CategoriesViewModel(repository) } }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(deepLink) {
        deepLink?.data?.let { uri ->
            val recipeId: Int? = when (uri.scheme) {
                "recipeapp" -> {
                    if (uri.host == "recipe") uri.pathSegments.getOrNull(0)?.toIntOrNull() else null
                }

                "https", "http" -> {
                    if (uri.pathSegments.getOrNull(0) == "recipe") uri.pathSegments.getOrNull(1)
                        ?.toIntOrNull() else null
                }

                else -> null
            }
            if (recipeId != null) {
                delay(100.milliseconds)
                navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                onDeepLink()
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = Destination.Categories.route,
        modifier = modifier
    ) {
        composable(Destination.Categories.route) {
            CategoriesScreen(
                uiState = uiState,
                onRetry = viewModel::loadCategories,
                onCategoryClick = { categoryId ->
                    navController.navigate(Destination.Recipes.createRoute(categoryId))
                },
            )
        }
        composable(Destination.Favorites.route) {
            FavoritesScreen(
                repository = repository,
                favoriteDataStoreManager = favoriteDataStoreManager,
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                }
            )
        }
        composable(
            route = Destination.Recipes.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) {
            val categoryId = it.arguments?.getInt("categoryId") ?: 0
            RecipesScreen(
                repository = repository,
                categoryId = categoryId,
                onRecipeClick = { recipeId, recipe ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        Destination.RecipeDetails.KEY_RECIPE_OBJECT,
                        recipe
                    )
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                }
            )
        }
        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) {
            val recipeId = it.arguments?.getInt("recipeId") ?: 0
            RecipeDetailsScreen(recipeId = recipeId, repository = repository)
        }
    }
}
