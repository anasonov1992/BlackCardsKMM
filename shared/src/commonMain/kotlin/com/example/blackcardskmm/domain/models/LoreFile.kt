package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.FileDto
import com.example.blackcardskmm.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow

data class LoreFile(
    private val guid: String,
    private val name: String,
    private val extension: String,
    val imageUrl: String? = null,
    val downloadedUri: MutableStateFlow<String?> = MutableStateFlow(null)
){
    constructor(file: FileDto) : this(
        file.guid,
        file.name,
        file.extension,
        file.imageUrl?.replace("192.168.0.190", Constants.BASE_HOST)) //FIXME

    val fullName: String
        get() = "$name.$extension"

    val type: String
        get() = extension.uppercase()

    val url: String
        get() = "${Constants.BASE_URL}/api/download/$guid"
}