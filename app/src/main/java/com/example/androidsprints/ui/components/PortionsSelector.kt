@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidsprints.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.example.androidsprints.R
import kotlin.math.roundToInt

@Composable
fun PortionsSelector(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit
) {
    Text(
        text = pluralStringResource(
            id = R.plurals.portions_count,
            count = currentPortions,
            currentPortions
        )
    )
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = 1f..12f,
        steps = 10
    )
}