package com.example.blackcardskmm.components.lore

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.domain.models.FractionSelectionModel
import com.example.blackcardskmm.domain.models.LoreFile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface LoreStore : Store<LoreStore.Intent, LoreStore.State, Nothing> {
    sealed class Intent {
        data class SearchActivated(val isActive: Boolean): Intent()
        data class SearchProcessing(val search: String): Intent()
        data object SearchCleared: Intent()
        data object FiltersApplied: Intent()
    }

    data class State(
        val files: ImmutableList<LoreFile> = emptyList<LoreFile>().toImmutableList(),
        val fractions: ImmutableList<FractionSelectionModel> = emptyList<FractionSelectionModel>().toImmutableList(),
        val search: String = "",
        val isSearchActive: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    ) {
        companion object {
            val EMPTY = State()
        }

        fun getAppliedFilters() = fractions.filter { it.isSelected.value }.map { it.id }.toList()
    }
}