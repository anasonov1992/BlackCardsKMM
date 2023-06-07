package com.example.blackcardskmm.components.cards

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.domain.models.CardArtDetail

interface CardArtDetailStore : Store<Nothing, CardArtDetailStore.State, Nothing> {
    data class State(
        val detail: CardArtDetail = CardArtDetail(),
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val error: String? = null
    ) {
        companion object {
            val EMPTY = State()
        }
    }
}