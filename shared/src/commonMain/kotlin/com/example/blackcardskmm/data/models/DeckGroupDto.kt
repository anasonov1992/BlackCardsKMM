package com.example.blackcardskmm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DeckGroupDto(
    val fraction: FractionShortDto,
    val decks: List<DeckShortDto>
)
