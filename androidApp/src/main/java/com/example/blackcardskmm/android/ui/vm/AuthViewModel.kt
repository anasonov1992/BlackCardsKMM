package com.example.blackcardskmm.android.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.blackcardskmm.domain.repository.AuthRepository
import com.example.blackcardskmm.util.*

class AuthViewModel constructor(
    private val repository: AuthRepository,
    private val dispatchers: CustomDispatchers,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
//    private val loadingStatus = ObservableLoader()
//
//    private val email = SavedMutableState(
//        savedStateHandle,
//        USERNAME_KEY,
//        defValue = "anton.nasonov3@gmail.com" //FIXME
//    )
//
//    private val password = SavedMutableState(
//        savedStateHandle,
//        PASSWORD_KEY,
//        defValue = "tonypower2023" //FIXME
//    )
//
//    private val passwordVisibility = SavedMutableState(
//        savedStateHandle,
//        PASSWORD_VIS_KEY,
//        defValue = false
//    )
//
//    val state = combineFlows(
//        email.flow,
//        password.flow,
//        passwordVisibility.flow,
//        loadingStatus.flow
//    ) { email, password, passwordVisibility, isLoading ->
//        AuthState(
//            email = email,
//            password = password,
//            passwordVisibility = passwordVisibility,
//            isLoading = isLoading
//        )
//    }.stateIn(
//        coroutineScope = viewModelScope + dispatchers.main,
//        initialValue = AuthState.EMPTY
//    )
//
//    init {
//        viewModelScope.launch {
//            try {
//                repository.isAuthenticated()
//                    .onStart { loadingStatus.start() }
//                    .onCompletion { loadingStatus.stop() }
//                    .collectLatest { result ->
//                        _uiEvent.emit(
//                            when (result) {
//                                is Result.Success -> success()
//                                is Result.Error -> showMessage(result.message)
//                            }
//                        )
//                    }
//            }
//            catch (ex: java.lang.Exception) {
//                _uiEvent.emit(showMessage(ex.localizedMessage))
//            }
//        }
//    }
//
//    private fun login() {
//        viewModelScope.launch {
//            repository.login(LoginRequestDto(email.value, password.value))
//                .onStart { loadingStatus.start() }
//                .onCompletion { loadingStatus.stop() }
//                .collectLatest { result ->
//                    _uiEvent.emit(
//                        when (result) {
//                            is Result.Success -> success()
//                            is Result.Error -> showMessage(result.message)
//                        }
//                    )
//                }
//        }
//    }
//
//    companion object {
//        const val USERNAME_KEY = "bc_auth_username"
//        const val PASSWORD_KEY = "bc_auth_password"
//        const val PASSWORD_VIS_KEY = "bc_auth_password_visibility"
//    }
}