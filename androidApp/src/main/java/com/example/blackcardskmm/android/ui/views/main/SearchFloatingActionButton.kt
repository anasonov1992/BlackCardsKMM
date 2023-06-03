package com.example.blackcardskmm.android.ui.views.main

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable

@Composable
fun CreateDeckFloatingActionButton(
    onClick: () -> Unit = {}
) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Create,
            contentDescription = "Create",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}