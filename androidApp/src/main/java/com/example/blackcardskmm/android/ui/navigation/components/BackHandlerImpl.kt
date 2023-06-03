package com.example.blackcardskmm.android.ui.navigation.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.example.blackcardskmm.android.ui.navigation.interfaces.BackHandler

class BackHandlerImpl : BackHandler {
    @Composable
    override fun invoke(enabled: Boolean, onBack: () -> Unit) {
        BackHandler(enabled, onBack)
    }
}