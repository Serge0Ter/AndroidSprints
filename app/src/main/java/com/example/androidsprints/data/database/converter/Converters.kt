package com.example.androidsprints.data.database.converter

import kotlinx.serialization.json.Json

object Converters {
    inline fun <reified T> listToJson(value: List<T>): String = Json.encodeToString(value)

    inline fun <reified T> jsonToList(value: String): List<T> = Json.decodeFromString(value)
}
