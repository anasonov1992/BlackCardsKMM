package com.example.blackcardskmm.data.models

import com.example.blackcardskmm.data.primitives.FractionType
import kotlinx.serialization.Serializable

@Serializable
data class FractionShortDto(
    val id: Int,
    val type: FractionType,
    val name: String,
    val logoUrl: String? = null
)
