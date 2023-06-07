package com.example.blackcardskmm.android.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.theme.BlackPrimary
import com.example.blackcardskmm.components.startup.StartupComponent
import kotlinx.coroutines.delay

@Composable
fun StartCover(
    component: StartupComponent
) {
    val state by component.state.collectAsStateWithLifecycle()

    Image(
        painter = painterResource(id = R.drawable.cover),
        "logo",
        modifier = Modifier
            .fillMaxSize()
            .background(BlackPrimary)
    )

    LaunchedEffect(Unit) {
        delay(state.coverDuration)
        component.onOutput(StartupComponent.Output.NavigateToAppStart)
    }
}