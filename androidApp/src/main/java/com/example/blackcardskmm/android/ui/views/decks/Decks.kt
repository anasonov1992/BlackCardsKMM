package com.example.blackcardskmm.android.ui.views.decks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.components.decks.DecksComponent
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun Decks(
    component: DecksComponent
) {
    val state by component.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pergament),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
        CollapsingToolbarScaffold(
            state = rememberCollapsingToolbarScaffoldState(),
            scrollStrategy = ScrollStrategy.EnterAlways,
            toolbar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { component.onOutput(DecksComponent.Output.NavigateBack) }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Колоды",
                            fontFamily = mikadanFont
                        )
                    }
                )
            },
            modifier = Modifier
        ) {
            DecksPager(state) { fractionId, deckId ->
                component.onOutput(DecksComponent.Output.NavigateToDeck(fractionId, deckId))
            }
        }
    }
}