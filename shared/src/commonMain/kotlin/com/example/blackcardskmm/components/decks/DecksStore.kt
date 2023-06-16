package com.example.blackcardskmm.components.decks

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.domain.models.DeckGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

interface DecksStore : Store<Nothing, DecksStore.State, Nothing> {
    data class State(
        val myDecks: ImmutableList<DeckGroup> = emptyList<DeckGroup>().toImmutableList(),
        val sharedDecks: ImmutableList<DeckGroup> = emptyList<DeckGroup>().toImmutableList(),
        val isLoading: Boolean = false,
        val error: String? = null
    ) {
        companion object {
            val EMPTY = State()
        }
    }
}