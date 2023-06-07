package com.example.blackcardskmm.android.ui.views

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.blackcardskmm.android.R
import kotlinx.coroutines.delay

@Composable
fun Splash(

) {
    var startAnimation by remember { mutableStateOf(false) }
    val transition = updateTransition(
        targetState = startAnimation,
        label = ""
    )
    val animationDuration = 3000
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenPadding = 20.dp
    val logoHeight = 180.dp

    val logoOffset by transition.animateFloat(
        transitionSpec = { tween(animationDuration) },
        label = ""
    ) {
        if(it) screenHeight/2F else 0F
    }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(animationDuration) },
        label = ""
    ) {
        if(it) 1F else 0F
    }

    Box(Modifier
        .padding(vertical = screenPadding)
        .fillMaxSize()) {
        Image(
            ImageVector.vectorResource(id = R.drawable.ic_logo),
            "logo",
            Modifier
                .height(logoHeight)
                .align(Alignment.TopCenter)
                .offset(y = logoOffset.dp - logoHeight - screenPadding)
                .alpha(alpha)
        )
        Column(Modifier
            .offset(y = (screenHeight/2F).dp)
            .alpha(alpha)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "XHack",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = "Full Compose Demo",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
        }
    }

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(animationDuration.toLong())
        //TODO intent
    }
}