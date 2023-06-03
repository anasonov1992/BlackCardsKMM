package com.example.blackcardskmm.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@Composable
fun PasswordTextField(
    label: String = "Пароль",
    value: String,
    onValueChange: (String) -> Unit = {  },
    enabled: Boolean = true,
    passwordVisibility: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit = {  },
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    val visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, fontWeight = FontWeight.Bold, fontFamily = mikadanFont, color = MaterialTheme.colors.primary)
        },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = mikadanFont, color = MaterialTheme.colors.primary),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {
            PasswordVisibilityIcon(
                passwordVisibility = passwordVisibility,
                onPasswordVisibilityChange = onPasswordVisibilityChange
            )
        },
        keyboardActions = onAction
    )
}