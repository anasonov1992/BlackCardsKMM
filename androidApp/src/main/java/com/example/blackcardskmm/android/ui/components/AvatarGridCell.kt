package com.example.blackcardskmm.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.extensions.imagePlaceholder
import com.example.blackcardskmm.android.ui.extensions.textPlaceholder
import com.example.blackcardskmm.android.ui.theme.TextColor

@Composable
fun AvatarGridCell(
    size: Dp = 90.dp,
    avatarUrl: String? = null,
    avatarPlaceholder: Int? = null,
    title: String? = null,
    hasLoadingPlaceholder: Boolean = false,
    onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .padding(vertical = 8.dp)
            .clickable { onClick.invoke() }
    ) {
        AvatarImage(
            size = size,
            avatarUrl = avatarUrl,
            placeholderResource = avatarPlaceholder ?: R.drawable.ic_user_avatar,
            modifier = Modifier.imagePlaceholder(corners = size / 2, isVisible = hasLoadingPlaceholder)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title ?: "Unknown",
            color = TextColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(90.dp)
                .textPlaceholder(isVisible = hasLoadingPlaceholder)
        )
    }
}