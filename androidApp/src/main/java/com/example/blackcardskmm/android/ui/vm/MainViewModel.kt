package com.example.blackcardskmm.android.ui.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.domain.repository.FractionsRepository
import com.example.blackcardskmm.android.ui.states.MainScreenState
import com.example.blackcardskmm.util.Result
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repository: FractionsRepository
) : ViewModel() {
    var state by mutableStateOf(MainScreenState.EMPTY)

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            repository.getFractions(fetchFromRemote = true).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            fractions = result.data.toImmutableList(),
                            //FIXME handle error another way
//                            error = null
                        )
                    }
                    is Result.Error -> {
                        //FIXME
                        //state = MainScreenState.EMPTY

                        state = state.copy(
                            fractions = listOf(
                                Fraction(
                                    type = FractionType.Tulin,
                                    name = "Королевство Тулин"
                                ),
                                Fraction(
                                    type = FractionType.Church,
                                    name = "Кардиналистская Церковь"
                                )
                            ).toImmutableList()
                            //FIXME handle error another way
//                            error = null
                        )

                        //FIXME handle error another way
//                      error = result.message
                    }
                }
            }
        }
    }
}
