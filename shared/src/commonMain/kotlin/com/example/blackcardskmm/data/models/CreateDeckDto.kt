package com.example.blackcardskmm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateDeckDto(
    val fractionId: Int,
    val deckName: String,
    val cardIds: List<CardInDeckPairDto>
)

@Serializable
data class CardInDeckPairDto(
    val id: Int,
    val amountInDeck: Int
)
