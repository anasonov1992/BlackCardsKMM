package com.example.blackcardskmm.data.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeckCardsRequestDto(
    val deckId: Int,
    val fractionId: Int
)
