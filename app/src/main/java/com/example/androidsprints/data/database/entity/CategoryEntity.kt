package com.example.androidsprints.data.database.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity("categories")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)
