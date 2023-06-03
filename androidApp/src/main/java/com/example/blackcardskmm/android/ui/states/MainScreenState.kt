package com.example.blackcardskmm.android.ui.states

import com.example.blackcardskmm.domain.models.Fraction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MainScreenState(
    val fractions: ImmutableList<Fraction> = emptyList<Fraction>().toImmutableList()
) {
    companion object {
        val EMPTY = MainScreenState()
    }
}

