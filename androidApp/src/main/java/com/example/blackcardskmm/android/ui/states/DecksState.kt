package com.example.blackcardskmm.android.ui.states

import com.example.blackcardskmm.domain.models.DeckGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class DecksState(
    val myDecks: ImmutableList<DeckGroup> = emptyList<DeckGroup>().toImmutableList(),
    val sharedDecks: ImmutableList<DeckGroup> = emptyList<DeckGroup>().toImmutableList()
) {
    companion object {
        val EMPTY = DecksState()
    }
}
