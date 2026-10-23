package com.example.androidsprints.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
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
    onShareClick: () -> Unit = {}
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
        if (showShareButton) {
            IconButton(
                onClick = onShareClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Dimens.PaddingMain),
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = stringResource(R.string.share)
                    )
                }
            )
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
