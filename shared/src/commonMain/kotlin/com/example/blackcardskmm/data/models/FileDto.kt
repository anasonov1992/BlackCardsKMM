package com.example.blackcardskmm.data.models

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class FileDto(
    @Contextual
    val guid: Uuid,
    val name: String,
    val extension: String,
    val imageUrl: String?
)
