package com.example.androidsprints.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidsprints.ui.categories.CategoriesScreen
import com.example.androidsprints.ui.details.RecipeDetailsScreen
import com.example.androidsprints.ui.favorites.FavoritesScreen
import com.example.androidsprints.ui.recipes.RecipesScreen

@Composable
fun RecipesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
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
