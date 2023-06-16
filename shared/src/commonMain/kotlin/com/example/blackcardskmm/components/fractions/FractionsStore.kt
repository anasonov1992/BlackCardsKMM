package com.example.blackcardskmm.components.fractions

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.domain.models.Fraction

interface FractionsStore: Store<Nothing, FractionsStore.State, Nothing> {
    data class State(
        val fractions: List<Fraction> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    ) {
        companion object {
            val EMPTY = State()
        }
    }
}