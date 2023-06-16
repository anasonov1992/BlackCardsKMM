package com.example.blackcardskmm.android.ui.views.decks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.blackcardskmm.android.ui.views.fractions.ShortFractionCard
import com.example.blackcardskmm.components.decks.DecksStore

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyDecks(
    state: DecksStore.State,
    modifier: Modifier = Modifier,
    onDeckNavigate: (Int, Int, String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        state.myDecks.forEach { (fraction, decks) ->
            stickyHeader(key = fraction.id) {
                //FIXME create actual UI
                ShortFractionCard(fraction)
            }
            items(decks) {
                ShortDeckCard(it) { fractionId, deckId, deckName ->
                    onDeckNavigate(fractionId, deckId, deckName)
                }
            }
        }
    }
}