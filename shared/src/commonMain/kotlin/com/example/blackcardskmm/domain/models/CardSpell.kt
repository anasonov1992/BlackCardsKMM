package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.CardSpellDto
import com.example.blackcardskmm.util.Constants

data class CardSpell(
    val id: Int = 0,
    val name: String,
    val types: List<SpellTypeModel>,
    val flavor: String,
    val description: String,
    val imageUrl: String? = null
) {
    constructor(data: CardSpellDto): this(
        data.id,
        data.name,
        data.types.map { SpellTypeModel(it) },
        data.flavor,
        data.description,
        data.imageUrl?.replace("192.168.0.190", Constants.BASE_HOST)) //FIXME
}