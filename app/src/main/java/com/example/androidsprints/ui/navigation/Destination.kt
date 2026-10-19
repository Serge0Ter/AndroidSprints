package com.example.androidsprints.ui.navigation


sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Recipes : Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }

    object RecipeDetails : Destination("recipe_details/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe_details/$recipeId"
        const val KEY_RECIPE_OBJECT = "recipe_object"
    }

    object Favorites : Destination("favorites")
}

