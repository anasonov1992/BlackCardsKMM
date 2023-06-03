package com.example.blackcardskmm.android.ui.extensions

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

fun Modifier.textPlaceholder(isVisible: Boolean): Modifier = composed {
    this.placeholder(
        visible = isVisible,
        shape = MaterialTheme.shapes.medium,
        highlight = PlaceholderHighlight.shimmer()
    )
}

fun Modifier.imagePlaceholder(corners: Dp, isVisible: Boolean): Modifier = composed {
    this.placeholder(
        visible = isVisible,
        shape = RoundedCornerShape(corners),
        highlight = PlaceholderHighlight.shimmer()
    )
}