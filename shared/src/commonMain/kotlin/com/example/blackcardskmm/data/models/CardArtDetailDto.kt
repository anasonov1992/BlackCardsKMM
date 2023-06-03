package com.example.blackcardskmm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CardArtDetailDto(
    val id: Int,
    val name: String,
    val description: String,
    val artUrl: String? = null,
    val fraction: String
)
