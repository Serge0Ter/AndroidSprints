package com.example.androidsprints.app.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidsprints.core.utils.NavArgs
import com.example.androidsprints.data.repository.RecipesRepository
import com.example.androidsprints.features.categories.ui.CategoriesScreen
import com.example.androidsprints.features.details.ui.RecipeDetailsScreen
import com.example.androidsprints.features.favorites.ui.FavoritesScreen
import com.example.androidsprints.features.recipes.ui.RecipesScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RecipesNavHost(
    repository: RecipesRepository,
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
                repository = repository,
                onCategoryClick = { categoryId, categoryTitle, categoryImage ->
                    navController.navigate(
                        Destination.Recipes.createRoute(
                            categoryId,
                            categoryTitle,
                            categoryImage
                        )
                    )
                },
            )
        }
        composable(Destination.Favorites.route) {
            FavoritesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                }
            )
        }
        composable(
            route = Destination.Recipes.route,
            arguments = listOf(
                navArgument(NavArgs.CATEGORY_ID) { type = NavType.IntType },
                navArgument(NavArgs.CATEGORY_TITLE) { type = NavType.StringType },
                navArgument(NavArgs.CATEGORY_IMAGE_URL) { type = NavType.StringType },
            )
        ) {
            RecipesScreen(
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
            arguments = listOf(navArgument(NavArgs.RECIPE_ID) { type = NavType.IntType })
        ) {
            val recipeId = it.arguments?.getInt(NavArgs.RECIPE_ID) ?: 0
            RecipeDetailsScreen(recipeId = recipeId, repository = repository)
        }
    }
}
