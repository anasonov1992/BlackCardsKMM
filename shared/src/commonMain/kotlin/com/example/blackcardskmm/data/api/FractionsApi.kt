package com.example.blackcardskmm.data.api

import com.example.blackcardskmm.data.models.FractionDto
import com.example.blackcardskmm.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

class FractionsApi(
    private val client: HttpClient,
    private val baseUrl: String = Constants.BASE_URL
): KoinComponent {
    suspend fun getFractions() = client
        .get("$baseUrl/api/getFractions")
        .body<List<FractionDto>>()
}