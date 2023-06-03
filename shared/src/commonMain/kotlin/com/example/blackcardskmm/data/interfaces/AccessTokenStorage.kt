package com.example.blackcardskmm.data.interfaces

interface AccessTokenStorage {
    fun saveAccessToken(token: String)
    fun getAccessToken(): String
}