package com.example.blackcardskmm.util

sealed class Result<out T: Any> {
    data class Error(val code: Int? = null, val message: String): Result<Nothing>()
    data class Success<out T: Any>(val data: T): Result<T>()
}