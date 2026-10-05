package com.example.androidsprints

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.theme.RecipesAppTheme

@Composable
fun RecipesApp() {
    RecipesAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Text(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = Dimens.PaddingMain),
                text = "Recipes App"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreviews() {
    RecipesApp()
}
