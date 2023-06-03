package com.example.blackcardskmm.android.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.blackcardskmm.android.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.example.blackcardskmm.android.ui.navigation.interfaces.CommonNavigator
import com.example.blackcardskmm.android.ui.navigation.models.NavigationEvent
import com.example.blackcardskmm.android.ui.theme.BlackPrimary
import kotlinx.coroutines.delay

@RootNavGraph(start = true)
@Destination
@Composable
fun StartCover(
    navigator: CommonNavigator
) {
    val coverDuration = 3000L

    Image(
        painter = painterResource(id = R.drawable.cover),
        "logo",
        modifier = Modifier
            .fillMaxSize()
            .background(BlackPrimary)
    )

    LaunchedEffect(Unit) {
        delay(coverDuration)
        navigator.navigateEvent(NavigationEvent.NavigateUp)
        navigator.navigateEvent(NavigationEvent.SignIn)
    }
}