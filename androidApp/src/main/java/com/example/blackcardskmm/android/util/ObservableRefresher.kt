package com.example.blackcardskmm.android.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

class ObservableRefresher {
    private val count = AtomicInteger()
    private val refreshingState = MutableStateFlow(count.get())

    val flow: Flow<Boolean>
        get() = refreshingState.map { it > 0 }.distinctUntilChanged()

    val isRefreshing get() = count.get() > 0

    fun start() {
        refreshingState.value = count.incrementAndGet()
    }

    fun stop() {
        refreshingState.value = count.decrementAndGet()
    }
}