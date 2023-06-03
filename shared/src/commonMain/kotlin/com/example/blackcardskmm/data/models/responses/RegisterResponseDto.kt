package com.example.blackcardskmm.data.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val token: String
)