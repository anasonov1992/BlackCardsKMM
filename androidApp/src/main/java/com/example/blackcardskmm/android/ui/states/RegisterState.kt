package com.example.blackcardskmm.android.ui.states

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val repeatedPassword: String = "",
    val passwordVisibility: Boolean = false,
    val isLoading: Boolean = false
) {
    fun isValid(): Boolean {
        return username.trim().isNotEmpty()
                && email.trim().isNotEmpty()
                && password.trim().isNotEmpty()
                && repeatedPassword.trim().isNotEmpty()
    }

    companion object {
        val EMPTY = RegisterState()
    }
}