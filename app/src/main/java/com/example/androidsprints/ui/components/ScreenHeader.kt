package com.example.androidsprints.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.androidsprints.R
import com.example.androidsprints.ui.theme.Dimens
import com.example.androidsprints.ui.theme.recipesAppTypography

@Composable
fun ScreenHeader(
    imagePainter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier,
    showShareButton: Boolean = false,
    onShareClick: () -> Unit = {},
    showFavoriteButton: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteToggle: () -> Unit = {}
) {
    Box(
        modifier = modifier.height(Dimens.HeaderHeight)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = imagePainter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(Dimens.PaddingSmall)
        ) {
            if (showFavoriteButton) {
                IconButton(
                    onClick = onFavoriteToggle,
                    content = {
                        Crossfade(
                            targetState = isFavorite,
                            modifier = Modifier,
                            animationSpec = tween(durationMillis = 300),
                            content = { isCurrentFavorite ->
                                val hearIcon = rememberVectorPainter(
                                    image = ImageVector.vectorResource(
                                        id = if (isCurrentFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
                                    )
                                )
                                Icon(
                                    painter = hearIcon,
                                    contentDescription = stringResource(id = R.string.favorites),
                                    tint = Color.Unspecified
                                )
                            }
                        )
                    }
                )
            }
            if (showShareButton) {
                IconButton(
                    onClick = onShareClick,
                    content = {
                        Icon(
                            painter = rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_share)),
                            contentDescription = stringResource(R.string.share),
                            tint = Color.Unspecified
                        )
                    }
                )
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Dimens.PaddingMain, bottom = Dimens.PaddingMain),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(Dimens.RoundedCorner8)
        ) {
            Text(
                modifier = Modifier.padding(Dimens.PaddingMediumLarge),
                text = title.uppercase(),
                color = MaterialTheme.colorScheme.onSurface,
                style = recipesAppTypography.displayLarge
            )
        }
    }
}
