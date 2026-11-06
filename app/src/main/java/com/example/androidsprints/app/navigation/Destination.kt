package com.example.androidsprints.app.navigation

import com.example.androidsprints.core.utils.NavArgs
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Recipes :
        Destination("recipes/{${NavArgs.CATEGORY_ID}}/{${NavArgs.CATEGORY_TITLE}}/{${NavArgs.CATEGORY_IMAGE_URL}}") {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImage: String): String {
            val encoderTitle = URLEncoder.encode(categoryTitle, StandardCharsets.UTF_8.toString())
            val encoderImage = URLEncoder.encode(categoryImage, StandardCharsets.UTF_8.toString())
            return "recipes/$categoryId/$encoderTitle/$encoderImage"
        }
    }

    object RecipeDetails : Destination("recipe/{${NavArgs.RECIPE_ID}}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
        const val KEY_RECIPE_OBJECT = "recipe_object"
    }

    object Favorites : Destination("favorites")
}

