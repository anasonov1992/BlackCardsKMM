package com.example.blackcardskmm.android.ui.events

sealed class RegistrationEvent {
    data class UsernameChanged(val value: String): RegistrationEvent()
    data class EmailChanged(val value: String): RegistrationEvent()
    data class PasswordChanged(val value: String): RegistrationEvent()
    data class RepeatedPasswordChanged(val value: String): RegistrationEvent()
    data class PasswordVisibilityChanged(val value: Boolean): RegistrationEvent()
    object RegistrationConfirmed: RegistrationEvent()
}
