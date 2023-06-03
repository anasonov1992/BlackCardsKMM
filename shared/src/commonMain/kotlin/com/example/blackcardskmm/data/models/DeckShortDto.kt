package com.example.blackcardskmm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DeckShortDto(
    val id: Int,
    val name: String,
    val fractionId: Int
)
