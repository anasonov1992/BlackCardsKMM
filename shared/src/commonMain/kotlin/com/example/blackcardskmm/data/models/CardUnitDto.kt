package com.example.blackcardskmm.data.models

import com.example.blackcardskmm.data.primitives.CardUniqueType
import kotlinx.serialization.Serializable

@Serializable
data class CardUnitDto(
    val id: Int,
    val rank: RankDto,
    val name: String,
    val uniqueType: CardUniqueType,
    val classes: List<UnitClassDto>,
    val flavor: String,
    val description: String,
    val imageUrl: String?
)
