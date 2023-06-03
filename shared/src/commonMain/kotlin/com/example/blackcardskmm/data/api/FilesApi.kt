package com.example.blackcardskmm.data.api

import com.example.blackcardskmm.data.models.FileDto
import com.example.blackcardskmm.data.models.requests.SearchRequestDto
import com.example.blackcardskmm.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

class FilesApi(
    private val client: HttpClient,
    private val baseUrl: String = Constants.BASE_URL
): KoinComponent {
    //FIXME
//    suspend fun download(guid: UUID) = client
//        .get("$baseUrl/api/download/$guid")

    suspend fun getFiles(request: SearchRequestDto) = client
        .post("$baseUrl/api/getFiles") {
            setBody(request)
        }
        .body<List<FileDto>>()
}