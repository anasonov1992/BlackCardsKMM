package com.example.blackcardskmm.data.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class CardsPagingRequestDto(
    val fractionId: Int,
    val pageSize: Int = Int.MAX_VALUE,
    val pageNumber: Int = 0
)