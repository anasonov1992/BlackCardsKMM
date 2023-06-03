package com.example.blackcardskmm.android.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.blackcardskmm.android.R

@Composable
fun PasswordVisibilityIcon(
    passwordVisibility: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit
) {
    val description = stringResource(id = if (passwordVisibility) R.string.hide_password else R.string.show_password)
    val icon = painterResource(id = if (passwordVisibility) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off)

    IconButton(onClick = { onPasswordVisibilityChange(!passwordVisibility) }) {
        Icon(painter = icon, contentDescription = description, tint = MaterialTheme.colors.primary)
    }
}