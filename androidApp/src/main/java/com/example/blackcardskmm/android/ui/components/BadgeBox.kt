package com.example.blackcardskmm.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.android.ui.extensions.textPlaceholder

@Composable
fun BadgeBox(text: String = "Mobile",
             textColor: Color = MaterialTheme.colors.onPrimary,
             fontSize: TextUnit? = null,
             background: Brush? = null,
             backgroundColor: Color? = null,
             modifier: Modifier = Modifier,
             hasLoadingPlaceholder: Boolean = false) {

    var boxModifier = modifier
        .height(20.dp)
        .clip(RoundedCornerShape(10.dp))

     if(background != null) {
         boxModifier = boxModifier.background(background)
     }
     else {
        backgroundColor?.let { boxModifier = boxModifier.background(backgroundColor) }
     }

    Box(modifier = boxModifier.padding(horizontal = 10.dp)) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize ?: 14.sp,
            modifier = Modifier.textPlaceholder(isVisible = hasLoadingPlaceholder)
        )
    }
}