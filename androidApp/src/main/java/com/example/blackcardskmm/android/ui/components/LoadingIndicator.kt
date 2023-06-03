package com.example.blackcardskmm.android.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.android.ui.theme.BorderColor

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    val boxShape = RoundedCornerShape(12.dp)

    Box(modifier = modifier
        .clip(boxShape)
        .border(BorderStroke(1.dp, BorderColor), boxShape)
        .background(MaterialTheme.colors.primary)
        .padding(8.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onPrimary,
                modifier = modifier
                    .size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Загрузка...",
                color = MaterialTheme.colors.onPrimary,
                fontSize = 14.sp
            )
        }
    }
}