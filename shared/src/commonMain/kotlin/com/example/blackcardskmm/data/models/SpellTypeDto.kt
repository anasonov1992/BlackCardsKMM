package com.example.blackcardskmm.data.models

import com.example.blackcardskmm.data.primitives.SpellType
import kotlinx.serialization.Serializable

@Serializable
data class SpellTypeDto(
    val id: Int,
    val type: SpellType,
    val displayName: String
)
