package com.example.blackcardskmm.data.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class SearchPagingRequestDto(
    val filter: String? = null,
    val pageSize: Int,
    val pageNumber: Int
)
