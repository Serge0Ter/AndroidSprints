package com.example.androidsprints.core.utils

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.aisprints.ru"
    const val IMAGES_BASE_URL = "https://recipes.aisprints.ru/api/images/"
    const val TIMEOUT_15 = 15L
    const val TIMEOUT_30 = 30L
    fun createRecipeDeepLink(recipeId: Int) = "$DEEP_LINK_BASE_URL/recipe/$recipeId"
}