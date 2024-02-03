package com.example.blackcardskmm.components.auth

import com.arkivanov.mvikotlin.core.store.Store

interface AuthStore: Store<AuthStore.Intent, AuthStore.State, Nothing> {
    sealed class Intent {
        data class EmailChanged(val value: String): Intent()
        data class PasswordChanged(val value: String): Intent()
        data class PasswordVisibilityChanged(val value: Boolean): Intent()
        object SignInConfirmed: Intent()
    }

    data class State(
        val email: String = "anton.nasonov3@gmail.com", //FIXME
        val password: String = "tonypower2023", //FIXME
        val passwordVisibility: Boolean = false,
        val isLoading: Boolean = false,
        val isSignedIn: Boolean = false,
        val error: String? = null
    ) {
        fun isValid(): Boolean = email.trim().isNotEmpty() && password.trim().isNotEmpty()

        companion object {
            val EMPTY = State()
        }
    }
}