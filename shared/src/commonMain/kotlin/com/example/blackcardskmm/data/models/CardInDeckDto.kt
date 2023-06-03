package com.example.blackcardskmm.data.models

import com.example.blackcardskmm.data.primitives.CardUniqueType
import kotlinx.serialization.Serializable

@Serializable
data class CardInDeckDto(
    val id: Int,
    val uniqueType: CardUniqueType,
    val amountInDeck: Int,
    val imageUrl: String?,
    val imageFullUrl: String?
)
