package com.example.blackcardskmm.data.models

import com.example.blackcardskmm.data.primitives.UnitClassType
import kotlinx.serialization.Serializable

@Serializable
data class UnitClassDto(
    val id: Int,
    val type: UnitClassType,
    val displayName: String
)
