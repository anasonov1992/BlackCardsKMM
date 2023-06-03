package com.example.blackcardskmm.android.ui.views.cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@Composable
fun CardHeader(title: String) {
    Box(modifier = Modifier
        .background(MaterialTheme.colors.primary)
        .padding(16.dp)
        .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = mikadanFont
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}