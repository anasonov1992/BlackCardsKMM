package com.example.blackcardskmm.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.extensions.imagePlaceholder

@Composable
fun AvatarImage(
    size: Dp,
    avatarUrl: String? = null,
    placeholderResource: Int = R.drawable.ic_user_avatar,
    hasLoadingPlaceholder: Boolean = false,
    modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = avatarUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                placeholder(placeholderResource)
                transformations(CircleCropTransformation())
            }).build()
        ),
        contentDescription = null,
        modifier = modifier
            .size(size)
            .imagePlaceholder(corners = size / 2, isVisible = hasLoadingPlaceholder)
    )
}