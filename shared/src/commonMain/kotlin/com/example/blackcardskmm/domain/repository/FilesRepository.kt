package com.example.blackcardskmm.domain.repository

import com.example.blackcardskmm.data.models.requests.SearchRequestDto
import com.example.blackcardskmm.domain.models.LoreFile
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.Flow

interface FilesRepository {
    //FIXME
//    suspend fun downloadFile(guid: UUID)

    suspend fun getFiles(request: SearchRequestDto): Flow<Result<List<LoreFile>>>
}