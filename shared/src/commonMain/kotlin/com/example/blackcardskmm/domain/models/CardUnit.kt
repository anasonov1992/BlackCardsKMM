package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.CardUnitDto
import com.example.blackcardskmm.data.models.RankDto
import com.example.blackcardskmm.data.primitives.CardUniqueType
import com.example.blackcardskmm.util.Constants

data class CardUnit(
    val id: Int = 0,
    private val rank: RankDto,
    val name: String,
    val uniqueType: CardUniqueType,
    val classes: List<UnitClass>,
    val flavor: String,
    val description: String,
    val imageUrl: String? = null
) {
    constructor(data: CardUnitDto): this(
        data.id,
        data.rank,
        data.name,
        data.uniqueType,
        data.classes.map { UnitClass(it) },
        data.flavor,
        data.description,
        data.imageUrl?.replace("192.168.0.190", Constants.BASE_HOST)) //FIXME

    val rankText: String
        get() = "${rank.displayName}."

    val uniquenessText: String?
        get() =
            when(uniqueType) {
                CardUniqueType.Unique -> "Уникальная карта"
                CardUniqueType.NotMoreTwo -> "Не более двух в колоде"
                else -> null
            }
}