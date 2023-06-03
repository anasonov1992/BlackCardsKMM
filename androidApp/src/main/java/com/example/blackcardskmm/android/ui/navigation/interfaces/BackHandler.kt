package com.example.blackcardskmm.android.ui.navigation.interfaces

import androidx.compose.runtime.Composable

interface BackHandler {
    @Composable
    operator fun invoke(enabled: Boolean, onBack: () -> Unit)
}