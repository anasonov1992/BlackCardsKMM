package com.example.blackcardskmm.components.lore

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.data.models.requests.SearchRequestDto
import com.example.blackcardskmm.domain.models.FractionSelectionModel
import com.example.blackcardskmm.domain.models.LoreFile
import com.example.blackcardskmm.domain.repository.FilesRepository
import com.example.blackcardskmm.domain.repository.FractionsRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class LoreStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
    private val filesRepository by inject<FilesRepository>()
    private val fractionsRepository by inject<FractionsRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): LoreStore =
        object : LoreStore,
            Store<LoreStore.Intent, LoreStore.State, Nothing> by storeFactory.create(
                name = "LoreStore",
                initialState = LoreStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class SearchActivated(val isActive: Boolean): Msg()
        data object LoreFilesLoading: Msg()
        data class LoreFilesLoaded(val search: String = "", val files: ImmutableList<LoreFile>): Msg()
        data class LoreFilesFailed(val search: String = "", val error: String): Msg()
        data class FractionsLoaded(val fractions: ImmutableList<FractionSelectionModel>): Msg()
        data class FractionsFailed(val error: String): Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<LoreStore.Intent, Unit, LoreStore.State, Msg, Nothing>(dispatchers.main) {
        private val _textSearch = MutableStateFlow("")
        val textSearch: StateFlow<String> = _textSearch.asStateFlow()

        fun setTextSearch(filter: String) {
            _textSearch.value = filter
        }

        fun clearTextSearch() {
            _textSearch.value = ""
        }

        override fun executeAction(action: Unit, getState: () -> LoreStore.State) {
            setupTextSearch()
            loadFractions()
        }

        override fun executeIntent(intent: LoreStore.Intent, getState: () -> LoreStore.State): Unit =
            when (intent) {
                is LoreStore.Intent.SearchActivated -> dispatch(Msg.SearchActivated(isActive = intent.isActive))
                is LoreStore.Intent.SearchProcessing -> setTextSearch(filter = intent.search)
                // loadFiles(intent.search, filters = getState().getAppliedFilters())
                is LoreStore.Intent.SearchCleared -> clearTextSearch() // loadFiles()
                is LoreStore.Intent.FiltersApplied -> loadFiles(filters = getState().getAppliedFilters())
            }

        private fun setupTextSearch() {
            scope.launch {
                textSearch.debounce(500).collect { query ->
                    if (query.isNullOrEmpty()) {
                        loadFiles()
                    }
                    else {
                        loadFiles(query)
                    }
                }
            }
        }

        private fun loadFractions() {
            scope.launch {
                fractionsRepository.getFractions(fetchFromRemote = false).collectLatest { result ->
                    when (result) {
                        is Result.Success -> dispatch(Msg.FractionsLoaded(fractions = result.data.map { FractionSelectionModel(it) }.toImmutableList()))
                        is Result.Error -> dispatch(Msg.FractionsFailed(error = result.message))
                    }
                }
            }
        }

        private fun loadFiles(query: String = "", filters: List<Int> = emptyList()) {
            scope.launch {
                filesRepository.getFiles(SearchRequestDto(query))
                    .onStart { dispatch(Msg.LoreFilesLoading) }
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.LoreFilesLoaded(search = query, files = result.data.toImmutableList()))
                            is Result.Error -> dispatch(Msg.LoreFilesFailed(search = query, error = result.message))
                        }
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<LoreStore.State, Msg> {
        override fun LoreStore.State.reduce(msg: Msg): LoreStore.State =
            when (msg) {
                is Msg.SearchActivated -> copy(isLoading = false, isSearchActive = msg.isActive)
                is Msg.LoreFilesLoading -> copy(isLoading = true)
                is Msg.LoreFilesLoaded -> copy(isLoading = false, search = msg.search, files = msg.files)
                is Msg.LoreFilesFailed -> copy(isLoading = false, search = msg.search, error = msg.error)
                is Msg.FractionsLoaded -> copy(isLoading = false, fractions = msg.fractions)
                is Msg.FractionsFailed -> copy(isLoading = false, error = msg.error)
            }
    }
}