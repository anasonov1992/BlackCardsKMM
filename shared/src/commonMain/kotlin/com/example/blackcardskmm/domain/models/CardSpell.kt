package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.CardSpellDto

data class CardSpell(
    val id: Int = 0,
    val name: String,
    val types: List<SpellTypeModel>,
    val flavor: String,
    val description: String,
    val imageUrl: String? = null
) {
    constructor(data: CardSpellDto):
            this(data.id, data.name, data.types.map { SpellTypeModel(it) }, data.flavor, data.description, data.imageUrl)

}

