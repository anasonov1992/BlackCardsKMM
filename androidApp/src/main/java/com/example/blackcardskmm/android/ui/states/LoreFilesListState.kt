package com.example.blackcardskmm.android.ui.states

import com.example.blackcardskmm.domain.models.FractionSelectionModel
import com.example.blackcardskmm.domain.models.LoreFile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class LoreFilesListState(
    val files: ImmutableList<LoreFile> = emptyList<LoreFile>().toImmutableList(),
    val fractions: ImmutableList<FractionSelectionModel> = emptyList<FractionSelectionModel>().toImmutableList(),
    val isSearchActive: Boolean = false
) {
    companion object {
        val EMPTY = LoreFilesListState()
    }

    fun getAppliedFilters() = fractions.filter { it.isSelected.value }.map { it.id }.toList()
}
