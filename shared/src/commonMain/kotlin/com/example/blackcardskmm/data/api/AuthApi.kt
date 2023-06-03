package com.example.blackcardskmm.data.api

import com.example.blackcardskmm.data.models.requests.LoginRequestDto
import com.example.blackcardskmm.data.models.requests.RegisterRequestDto
import com.example.blackcardskmm.data.models.responses.LoginResponseDto
import com.example.blackcardskmm.data.models.responses.RegisterResponseDto
import com.example.blackcardskmm.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

class AuthApi(
    private val client: HttpClient,
    private val baseUrl: String = Constants.BASE_URL
): KoinComponent {
    suspend fun login(request: LoginRequestDto) = client.post("$baseUrl/api/login") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body<LoginResponseDto>()

    suspend fun register(request: RegisterRequestDto) = client.post("$baseUrl/api/register") {
        setBody(request)
    }.body<RegisterResponseDto>()
}