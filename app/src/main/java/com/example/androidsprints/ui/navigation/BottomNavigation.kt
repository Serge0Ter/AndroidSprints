package com.example.androidsprints.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidsprints.R
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.theme.recipesAppTypography

@Composable
fun BottomNavigation(
    favoriteCount: Int,
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
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
                text = stringResource(R.string.categories).uppercase(),
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
                text = stringResource(R.string.favorites).uppercase(),
                style = recipesAppTypography.titleMedium,
            )
            BadgedBox(
                badge = {
                    if (favoriteCount > 0) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(MaterialTheme.colorScheme.surface, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (favoriteCount > 99) "99+" else favoriteCount.toString(),
                                fontSize = 9.sp,
                                lineHeight = 9.sp,
                                letterSpacing = 0.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                content = {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.favorites)
                    )
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(
        favoriteCount = 1,
        onCategoriesClick = {},
        onFavoriteClick = {}
    )
}