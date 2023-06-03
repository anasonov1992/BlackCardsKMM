package com.example.blackcardskmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CircleBox(radius: Dp, color: Color, modifier: Modifier){
    Box(modifier = modifier
        .size(radius)
        .clip(RoundedCornerShape(radius/2))
        .background(color))
}