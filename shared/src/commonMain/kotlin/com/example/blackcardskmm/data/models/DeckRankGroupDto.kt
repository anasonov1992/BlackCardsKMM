package com.example.blackcardskmm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DeckRankGroupDto(
    val rank: RankDto,
    val cards: List<CardInDeckDto>
)
