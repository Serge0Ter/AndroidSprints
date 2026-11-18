package com.example.androidsprints.core.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.androidsprints.R
import com.example.androidsprints.ui.theme.Dimens

@Composable
fun ImageNetwork(
    imageURL: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    val request = remember(context, imageURL) {
        ImageRequest.Builder(context)
            .data(imageURL)
            .crossfade(Dimens.durationMillis)
            .build()
    }
    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.2f),
        contentScale = contentScale,
        model = request,
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.loading),
        error = painterResource(R.drawable.error)
    )
}