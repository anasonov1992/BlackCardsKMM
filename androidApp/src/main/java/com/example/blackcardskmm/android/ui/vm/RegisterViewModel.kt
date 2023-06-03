package com.example.blackcardskmm.android.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackcardskmm.android.ui.events.RegistrationEvent
import com.example.blackcardskmm.android.ui.events.UiEvent
import com.example.blackcardskmm.android.ui.events.showMessage
import com.example.blackcardskmm.android.ui.events.success
import com.example.blackcardskmm.android.ui.states.RegisterState
import com.example.blackcardskmm.util.ObservableLoader
import com.example.blackcardskmm.android.util.SavedMutableState
import com.example.blackcardskmm.android.util.combineFlows
import com.example.blackcardskmm.android.util.stateIn
import com.example.blackcardskmm.data.models.requests.RegisterRequestDto
import com.example.blackcardskmm.domain.repository.AuthRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class RegisterViewModel constructor(
    private val repository: AuthRepository,
    private val dispatchers: CustomDispatchers,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val loadingStatus = ObservableLoader()

    private val username = SavedMutableState(
        savedStateHandle,
        USERNAME_KEY,
        defValue = ""
    )

    private val email = SavedMutableState(
        savedStateHandle,
        EMAIL_KEY,
        defValue = ""
    )

    private val password = SavedMutableState(
        savedStateHandle,
        PASSWORD_KEY,
        defValue = ""
    )

    private val repeatedPassword = SavedMutableState(
        savedStateHandle,
        REPEATED_PASSWORD_KEY,
        defValue = ""
    )

    private val passwordVisibility = SavedMutableState(
        savedStateHandle,
        PASSWORD_VIS_KEY,
        defValue = false
    )

    val state = combineFlows(
        username.flow,
        email.flow,
        password.flow,
        repeatedPassword.flow,
        passwordVisibility.flow,
        loadingStatus.flow
    ) { username, email, password, repeatedPassword, passwordVisibility, isLoading ->
        RegisterState(
            username = username,
            email = email,
            password = password,
            repeatedPassword = repeatedPassword,
            passwordVisibility = passwordVisibility,
            isLoading = isLoading
        )
    }.stateIn(
        coroutineScope = viewModelScope + dispatchers.main,
        initialValue = RegisterState.EMPTY
    )

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.UsernameChanged -> {
                username.value = event.value
            }
            is RegistrationEvent.EmailChanged -> {
                email.value = event.value
            }
            is RegistrationEvent.PasswordChanged -> {
                password.value = event.value
            }
            is RegistrationEvent.RepeatedPasswordChanged -> {
                repeatedPassword.value = event.value
            }
            is RegistrationEvent.PasswordVisibilityChanged -> {
                passwordVisibility.value = event.value
            }
            is RegistrationEvent.RegistrationConfirmed -> {
                register()
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            repository.register(RegisterRequestDto(username.value, email.value, password.value))
                .onStart { loadingStatus.start() }
                .onCompletion { loadingStatus.stop() }
                .collectLatest { result ->
                    _uiEvent.emit(
                        when (result) {
                            is Result.Success -> success()
                            is Result.Error -> showMessage(result.message)
                        }
                    )
            }
        }
    }

    companion object {
        const val USERNAME_KEY = "bc_reg_username"
        const val EMAIL_KEY = "bc_reg_email"
        const val PASSWORD_KEY = "bc_reg_password"
        const val REPEATED_PASSWORD_KEY = "bc_reg_repeated_password"
        const val PASSWORD_VIS_KEY = "bc_reg_password_visibility"
    }
}