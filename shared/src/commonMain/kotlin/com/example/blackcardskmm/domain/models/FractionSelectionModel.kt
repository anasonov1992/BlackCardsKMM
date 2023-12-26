package com.example.blackcardskmm.domain.models

import kotlinx.coroutines.flow.MutableStateFlow

data class FractionSelectionModel(
    val id: Int = 0,
    val name: String = "Unknown",
    val logoUrl: String? = null,
    val isSelected: MutableStateFlow<Boolean> = MutableStateFlow(false)
) {
    constructor(fraction: Fraction) : this(fraction.id, fraction.name, fraction.logoUrl)

    fun select() {
        isSelected.value = !isSelected.value
    }
}