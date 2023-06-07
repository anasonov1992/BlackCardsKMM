package com.example.blackcardskmm.android.ui.views.decks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.states.DecksState
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun Decks(
//    component: DecksComponent
) {
//    val state by component.state.collectAsStateWithLifecycle()
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(id = R.drawable.pergament),
//            contentDescription = "",
//            modifier = Modifier
//                .matchParentSize(),
//            contentScale = ContentScale.Crop
//        )
//        CollapsingToolbarScaffold(
//            state = rememberCollapsingToolbarScaffoldState(),
//            scrollStrategy = ScrollStrategy.EnterAlways,
//            toolbar = {
//                TopAppBar(
//                    navigationIcon = {
//                        IconButton(onClick = { navigator.navigateEvent(NavigationEvent.NavigateUp) }) {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowBack,
//                                contentDescription = "Back",
//                                tint = MaterialTheme.colors.onPrimary
//                            )
//                        }
//                    },
//                    title = {
//                        Text(
//                            text = "Колоды",
//                            fontFamily = mikadanFont
//                        )
//                    }
//                )
//            },
//            modifier = Modifier
//        ) {
//            DecksPager(state) { fractionId, deckId, deckName ->
//                navigator.navigateEvent(NavigationEvent.CreateCardDeck(fractionId, deckName, deckId))
//            }
//        }
//    }
}