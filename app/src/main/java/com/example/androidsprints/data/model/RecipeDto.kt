package com.example.androidsprints.data.model

import com.example.androidsprints.data.database.converter.Converters
import com.example.androidsprints.data.database.entity.RecipeEntity
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
    val serving: Int = 4,
)

fun RecipeDto.toEntity(categoryId: Int): RecipeEntity = RecipeEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    ingredients = Converters.listToJson(ingredients),
    method = Converters.listToJson(method),
    imageUrl = imageUrl
)

fun RecipeEntity.toDto(): RecipeDto = RecipeDto(
    id = id,
    title = title,
    ingredients = Converters.jsonToList(ingredients),
    method = Converters.jsonToList(method),
    imageUrl = imageUrl,
)