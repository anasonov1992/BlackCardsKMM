package com.example.blackcardskmm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DeckDto(
    val id: Int,
    val name: String,
    val fraction: FractionDto
)
