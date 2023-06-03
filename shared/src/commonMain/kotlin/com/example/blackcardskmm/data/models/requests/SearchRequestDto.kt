package com.example.blackcardskmm.data.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class SearchRequestDto(
    val filter: String = ""
)
