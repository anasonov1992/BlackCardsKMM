package com.example.blackcardskmm.android.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoundedButton(
    backgroundColor: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(25.dp,),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.30f)
        ),
        modifier = modifier
            .size(50.dp)
    ) {
        content()
    }
}