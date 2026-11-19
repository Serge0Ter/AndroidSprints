package com.example.androidsprints.data.database.converter

import androidx.room3.ColumnTypeConverter

class Converters {
    @ColumnTypeConverter
    fun fromString(value: String): List<String> =
        if (value.isEmpty()) emptyList() else value.split("|||")

    @ColumnTypeConverter
    fun fromList(value: List<String>): String = value.joinToString("|||")
}
