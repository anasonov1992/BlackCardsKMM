package com.example.blackcardskmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.blackcardskmm.android.ui.extensions.imagePlaceholder

@Composable
fun RoundedImage(
    width: Dp,
    height: Dp,
    imageUrl: String?,
    corners: Float = 0.0f,
    contentScale: ContentScale = ContentScale.Inside,
    hasLoadingPlaceholder: Boolean = false,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                transformations(RoundedCornersTransformation(corners))
            }).build()
        ),
        contentScale = contentScale,
        contentDescription = null,
        modifier = modifier
            .size(width, height)
            .imagePlaceholder(corners = corners.dp, isVisible = hasLoadingPlaceholder)
    )
}