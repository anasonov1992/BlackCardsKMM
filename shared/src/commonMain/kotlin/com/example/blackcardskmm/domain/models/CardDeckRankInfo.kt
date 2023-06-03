package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.RankDto
import kotlinx.coroutines.flow.MutableStateFlow

data class CardDeckRankInfo(
    val rankId: Int = 0,
    val rankName: String = "",
    val maxCardsOfRankCount: Int = 0,
    val cardsUpText: String = "",
    val cardsOfRankCount: MutableStateFlow<Int> = MutableStateFlow(0), //FIXME: fill from backend
    val areCardsUpSet: MutableStateFlow<Boolean> = MutableStateFlow(false)
) {
    constructor(rank: RankDto): this(rank.id, rank.displayName, rank.maxCardsOfRankCount, rank.cardsUpText)

    val areCardsUp: Boolean
        get() = cardsOfRankCount.value == maxCardsOfRankCount

    val displayName: String
        get() = "$rankName - $maxCardsOfRankCount" //TODO карт карты

    fun setCardsUp() {
        areCardsUpSet.value = true
    }

    fun resetCardsUp() {
        areCardsUpSet.value = false
    }

    companion object {
        val EMPTY = CardDeckRankInfo()
    }
}