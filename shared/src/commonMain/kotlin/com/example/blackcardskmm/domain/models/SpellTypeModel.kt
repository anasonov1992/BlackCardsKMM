package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.SpellTypeDto
import com.example.blackcardskmm.data.primitives.SpellType

data class SpellTypeModel(
    val type: SpellType,
    val displayName: String
) {
    constructor(data: SpellTypeDto) : this(data.type, data.displayName)
}