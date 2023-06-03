package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.DeckGroupDto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class DeckGroup(
    val fraction: Fraction,
    val cards: ImmutableList<Deck> = emptyList<Deck>().toImmutableList()
) {
    constructor(data: DeckGroupDto): this(Fraction(data.fraction), data.decks.map { Deck(it) }.toImmutableList())
}
