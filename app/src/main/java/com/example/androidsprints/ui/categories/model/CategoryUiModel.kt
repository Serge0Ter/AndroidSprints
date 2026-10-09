package com.example.androidsprints.ui.categories.model

import androidx.compose.runtime.Immutable
import com.example.androidsprints.data.model.CategoryDto
import com.example.androidsprints.ui.components.Constants

@Immutable
data class CategoryUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

fun CategoryDto.toUiModel() =
    CategoryUiModel(
        id = id,
        title = title,
        description = description,
        imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl
    )