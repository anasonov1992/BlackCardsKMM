package com.example.blackcardskmm.data.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)