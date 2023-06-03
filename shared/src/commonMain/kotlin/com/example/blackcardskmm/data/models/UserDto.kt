package com.example.blackcardskmm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatarUrl: String? = null,
//    val description: String,
//    val isAvailableForSearching: Boolean,
//    val specialization: String,
)

val UserDto.fullname: String
    get() = "$firstName $lastName".trim()
