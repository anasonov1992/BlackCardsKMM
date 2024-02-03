package com.example.blackcardskmm.android.ui.states

import com.example.blackcardskmm.domain.models.CardDeckRankGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class CardDeckState(
    val maxCardsCount: Int = 0,
    val cardsDeck: ImmutableList<CardDeckRankGroup> = emptyList<CardDeckRankGroup>().toImmutableList()
) {
    val cardsInDeckCount: Int
        get() = cardsDeck.sumOf { it.rankInfo.cardsOfRankCount.value }

    val areCardsUpText: String
        get() = cardsDeck.firstOrNull { it.rankInfo.areCardsUpSet.value }?.rankInfo?.cardsUpText ?: ""

    companion object {
        val EMPTY = CardDeckState()
    }
}