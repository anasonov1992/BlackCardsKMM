package com.example.blackcardskmm.domain.repository

import com.example.blackcardskmm.data.models.requests.LoginRequestDto
import com.example.blackcardskmm.data.models.requests.RegisterRequestDto
import com.example.blackcardskmm.data.models.responses.LoginResponseDto
import com.example.blackcardskmm.data.models.responses.RegisterResponseDto
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isAuthenticated(): Flow<Result<Boolean>>

    suspend fun login(request: LoginRequestDto): Flow<Result<LoginResponseDto>>

    suspend fun register(request: RegisterRequestDto): Flow<Result<RegisterResponseDto>>
}