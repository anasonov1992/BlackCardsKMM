package com.example.blackcardskmm.components.register

import com.arkivanov.mvikotlin.core.store.Store

interface RegisterStore: Store<RegisterStore.Intent, RegisterStore.State, Nothing> {
    sealed class Intent {
        data class UsernameChanged(val value: String): Intent()
        data class EmailChanged(val value: String): Intent()
        data class PasswordChanged(val value: String): Intent()
        data class RepeatedPasswordChanged(val value: String): Intent()
        data class PasswordVisibilityChanged(val value: Boolean): Intent()
        object RegistrationConfirmed: Intent()
    }

    data class State(
        val username: String = "",
        val email: String = "",
        val password: String = "",
        val repeatedPassword: String = "",
        val passwordVisibility: Boolean = false,
        val isLoading: Boolean = false,
        val isRegistered: Boolean = false,
        val error: String? = null
    ) {
        fun isValid(): Boolean {
            return username.trim().isNotEmpty()
                    && email.trim().isNotEmpty()
                    && password.trim().isNotEmpty()
                    && repeatedPassword.trim().isNotEmpty()
        }

        companion object {
            val EMPTY = State()
        }
    }
}