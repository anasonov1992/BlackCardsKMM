package com.example.blackcardskmm.data.models

import com.example.blackcardskmm.data.primitives.FractionType
import kotlinx.serialization.Serializable

@Serializable
data class FractionDto(
    val id: Int,
    val type: FractionType,
    val name: String,
    val description: String,
    val artUrl: String? = null,
    val logoUrl: String? = null
)
