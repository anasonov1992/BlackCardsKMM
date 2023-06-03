package com.example.blackcardskmm.components.cards

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.domain.models.CardArt
import com.kuuurt.paging.multiplatform.PagingData
import kotlinx.coroutines.flow.*

interface CardArtsStore: Store<CardArtsStore.Intent, CardArtsStore.State, Nothing> {
    sealed class Intent {
        data class SearchActivated(val isActive: Boolean): Intent()
        data class SearchProcessing(val filter: String?): Intent()
        object SearchCleared: Intent()
    }

    data class State(
        val cardArts: Flow<PagingData<CardArt>> = emptyFlow(),
        val isSearchActive: Boolean = false,
        val isRefreshing: Boolean = false
    ) {
        private val _textSearch = MutableStateFlow("")
        val textSearch: StateFlow<String> = _textSearch.asStateFlow()

        companion object {
            val EMPTY = State()
        }
    }
}