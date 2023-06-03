package com.example.blackcardskmm.android.ui.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackcardskmm.android.ui.events.LoreFilesListEvent
import com.example.blackcardskmm.android.ui.states.LoreFilesListState
import com.example.blackcardskmm.data.models.requests.SearchRequestDto
import com.example.blackcardskmm.domain.models.FractionSelectionModel
import com.example.blackcardskmm.domain.repository.FilesRepository
import com.example.blackcardskmm.domain.repository.FractionsRepository
import com.example.blackcardskmm.util.Result
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoreViewModel constructor(
    private val filesRepo: FilesRepository,
    private val fractionsRepo: FractionsRepository
): ViewModel() {
    private val _textSearch = MutableStateFlow("")
    val textSearch: StateFlow<String> = _textSearch.asStateFlow()

    var state by mutableStateOf(LoreFilesListState.EMPTY)

    init {
        loadFiles()
        loadFractions()
        startTextSearchListening()
    }

    fun setSearchText(filter: String) {
        _textSearch.value = filter
    }

    fun clearSearchText() {
        _textSearch.value = ""
    }

    fun onEvent(event: LoreFilesListEvent) {
        when(event) {
            is LoreFilesListEvent.SearchActivate -> {
                state = state.copy(
                    isSearchActive = event.isActive
                )
            }
            is LoreFilesListEvent.SearchProcess -> {
                loadFiles(event.search, filters = state.getAppliedFilters())
            }
            is LoreFilesListEvent.SearchClear -> {
                loadFiles()
            }
            is LoreFilesListEvent.ApplyFilters -> {
                loadFiles(filters = state.getAppliedFilters())
            }
        }
    }

    private fun loadFiles(search: String = "", filters: List<Int> = emptyList()) {
        viewModelScope.launch {
            filesRepo.getFiles(SearchRequestDto(search)).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            files = result.data.toImmutableList()
                        )
                    }
                    is Result.Error -> {
                        //TODO
                    }
                }
            }
        }
    }

    private fun loadFractions() {
        viewModelScope.launch {
            fractionsRepo.getFractions(fetchFromRemote = false).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            fractions = result.data.map { FractionSelectionModel(it) }.toImmutableList()
                        )
                    }
                    is Result.Error -> {
                        //TODO
                    }
                }
            }
        }
    }

    private fun startTextSearchListening() {
        viewModelScope.launch {
            textSearch.debounce(TextSearchDuration).collect { query ->
                if (query.isNullOrEmpty()) {
                    onEvent(LoreFilesListEvent.SearchClear)
                }
                else {
                    onEvent(LoreFilesListEvent.SearchProcess(query))
                }
            }
        }
    }

    companion object {
        private const val TextSearchDuration = 500L
    }
}

