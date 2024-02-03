package com.example.blackcardskmm.android.ui.views.decks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.blackcardskmm.android.ui.views.fractions.ShortFractionCard
import com.example.blackcardskmm.components.decks.DecksStore

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyDecks(
    state: DecksStore.State,
    modifier: Modifier = Modifier,
    onDeckNavigate: (Int, Int) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        state.myDecks.forEach { (fraction, decks) ->
            stickyHeader(key = fraction.id) {
                ShortFractionCard(fraction)
            }
            items(decks) {
                ShortDeckCard(it) { fractionId, deckId ->
                    onDeckNavigate(fractionId, deckId)
                }
            }
        }
    }
}