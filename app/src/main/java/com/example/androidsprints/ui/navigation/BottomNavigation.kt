package com.example.androidsprints.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsprints.ScreenId
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.theme.recipesAppTypography

@Composable
fun BottomNavigation(onCategoriesClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = Dimens.PaddingMedium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(Dimens.RoundedCorner6),
            onClick = onCategoriesClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = ScreenId.CATEGORIES.title.uppercase(),
                style = recipesAppTypography.titleMedium
            )
        }
        Button(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(Dimens.RoundedCorner6),
            onClick = onFavoriteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(
                text = ScreenId.FAVORITES.title.uppercase(),
                style = recipesAppTypography.titleMedium,
            )
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = ScreenId.FAVORITES.title
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(
        onCategoriesClick = {},
        onFavoriteClick = {}
    )
}