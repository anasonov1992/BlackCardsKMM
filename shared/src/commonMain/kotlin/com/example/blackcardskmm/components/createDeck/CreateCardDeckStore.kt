package com.example.blackcardskmm.components.createDeck

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.domain.models.CardDeckRankGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

interface CreateCardDeckStore : Store<CreateCardDeckStore.Intent, CreateCardDeckStore.State, Nothing> {
    sealed class Intent {
        data object CreateCardDeck: Intent()
    }

    data class State(
        val maxCardsCount: Int = 0,
        val cardsDeck: ImmutableList<CardDeckRankGroup> = emptyList<CardDeckRankGroup>().toImmutableList(),
        val error: String? = null
    ) {
        val cardsInDeckCount: Int
            get() = cardsDeck.sumOf { it.rankInfo.cardsOfRankCount.value }

        val areCardsUpText: String
            get() = cardsDeck.firstOrNull { it.rankInfo.areCardsUpSet.value }?.rankInfo?.cardsUpText ?: ""

        companion object {
            val EMPTY = State()
        }
    }
}