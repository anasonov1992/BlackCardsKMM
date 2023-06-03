package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.UnitClassDto
import com.example.blackcardskmm.data.primitives.UnitClassType

data class UnitClass(
    val type: UnitClassType,
    val displayName: String
) {
    constructor(data: UnitClassDto) : this(data.type, data.displayName)
}