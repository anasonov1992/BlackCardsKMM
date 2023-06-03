package com.example.blackcardskmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform