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
        object SearchCleared: Intent()
        object FiltersApplied: Intent()
    }

    data class State(
        val files: ImmutableList<LoreFile> = emptyList<LoreFile>().toImmutableList(),
        val fractions: ImmutableList<FractionSelectionModel> = emptyList<FractionSelectionModel>().toImmutableList(),
        val isSearchActive: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    ) {
        companion object {
            val EMPTY = State()
        }

        private val _textSearch = MutableStateFlow("")
        val textSearch: StateFlow<String> = _textSearch.asStateFlow()

        fun setSearchText(filter: String) {
            _textSearch.value = filter
        }

        fun clearSearchText() {
            _textSearch.value = ""
        }

        fun getAppliedFilters() = fractions.filter { it.isSelected.value }.map { it.id }.toList()
    }
}