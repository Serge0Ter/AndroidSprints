package com.example.androidsprints.data.database.converter

import androidx.room3.ColumnTypeConverter
import kotlinx.serialization.json.Json

object Converters {
//    @ColumnTypeConverter
//    fun fromString(value: String): List<String> =
//        if (value.isEmpty()) emptyList() else value.split("|||")
//
//    @ColumnTypeConverter
//    fun fromList(value: List<String>): String = value.joinToString("|||")
//
//    @ColumnTypeConverter
//    fun fromIngredientString(value: List<IngredientDto>): String = Json.encodeToString(value)
//
//    @ColumnTypeConverter
//    fun fromIngredientList(value: String): List<IngredientDto> = Json.decodeFromString(value)

    inline fun <reified T> listToJson(value: List<T>): String = Json.encodeToString(value)

    inline fun <reified T> jsonToList(value: String): List<T> = Json.decodeFromString(value)
}
