package com.example.blackcardskmm.android.ui.events

sealed class LoreFilesListEvent {
    data class SearchActivate(val isActive: Boolean): LoreFilesListEvent()
    data class SearchProcess(val search: String): LoreFilesListEvent()
    object SearchClear: LoreFilesListEvent()
    object ApplyFilters: LoreFilesListEvent()
}
