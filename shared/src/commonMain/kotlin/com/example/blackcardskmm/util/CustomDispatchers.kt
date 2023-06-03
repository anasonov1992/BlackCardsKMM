package com.example.blackcardskmm.util

import kotlinx.coroutines.CoroutineDispatcher

data class CustomDispatchers(
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)
