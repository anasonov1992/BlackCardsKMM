package com.example.blackcardskmm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RankDto(
    val id: Int,
    val displayName: String,
    val maxCardsOfRankCount: Int,
    val cardsUpText: String
)
