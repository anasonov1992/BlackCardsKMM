package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.DeckRankGroupDto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class CardDeckRankGroup(
    val rankInfo: CardDeckRankInfo = CardDeckRankInfo.EMPTY,
    val cards: ImmutableList<CardInDeckModel> = emptyList<CardInDeckModel>().toImmutableList()
) {
    constructor(data: DeckRankGroupDto): this(CardDeckRankInfo(data.rank), data.cards.map { CardInDeckModel(it) }.toImmutableList())
}