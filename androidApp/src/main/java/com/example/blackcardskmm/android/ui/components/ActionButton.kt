package com.example.blackcardskmm.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@Composable
fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(48),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontFamily = mikadanFont,
            color = MaterialTheme.colors.onPrimary)
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    ActionButton("Register")
}