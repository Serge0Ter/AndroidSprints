package com.example.androidsprints.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidsprints.data.repository.getRecipeById
import com.example.androidsprints.ui.categories.CategoriesScreen
import com.example.androidsprints.ui.details.RecipeDetailsScreen
import com.example.androidsprints.ui.favorites.FavoritesScreen
import com.example.androidsprints.ui.recipes.RecipesScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RecipesNavHost(
    deepLink: Intent? = null,
    onDeepLink: () -> Unit = {},
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
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
                onCategoryClick = { categoryId ->
                    navController.navigate(Destination.Recipes.createRoute(categoryId))
                }
            )
        }
        composable(Destination.Favorites.route) {
            FavoritesScreen()
        }
        composable(
            route = Destination.Recipes.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) {
            val categoryId = it.arguments?.getInt("categoryId") ?: 0
            RecipesScreen(
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
            RecipeDetailsScreen(recipeId = recipeId)
        }
    }
}
