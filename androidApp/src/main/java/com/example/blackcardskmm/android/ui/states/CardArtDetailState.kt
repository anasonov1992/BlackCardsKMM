package com.example.blackcardskmm.android.ui.states

import com.example.blackcardskmm.domain.models.CardArtDetail

data class CardArtDetailState(
    val cardArt: CardArtDetail = CardArtDetail(),
    val isRefreshing: Boolean = false
) {
    companion object {
        val EMPTY = CardArtDetailState()
    }
}
