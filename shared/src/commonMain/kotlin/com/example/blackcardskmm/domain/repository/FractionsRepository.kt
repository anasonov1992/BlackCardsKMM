package com.example.blackcardskmm.domain.repository

import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.Flow

interface FractionsRepository {
    suspend fun getFractions(fetchFromRemote: Boolean): Flow<Result<List<Fraction>>>
}