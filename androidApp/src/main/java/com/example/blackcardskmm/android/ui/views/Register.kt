package com.example.blackcardskmm.android.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.components.*
import com.example.blackcardskmm.android.ui.events.RegistrationEvent
import com.example.blackcardskmm.android.ui.events.safeCollector
import com.example.blackcardskmm.android.ui.navigation.interfaces.CommonNavigator
import com.example.blackcardskmm.android.ui.navigation.models.NavigationEvent
import com.example.blackcardskmm.android.ui.vm.RegisterViewModel
import org.koin.androidx.compose.getViewModel

@Destination
@ExperimentalComposeUiApi
@Composable
fun Register(
    viewModel: RegisterViewModel = getViewModel(),
    navigator: CommonNavigator,
    scaffoldState: ScaffoldState
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val passwordFocusRequester = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current

    val spacer = 10.dp

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pergament),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.bc_logo),
                "logo",
                modifier = Modifier
                    .size(220.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                label = "Имя пользователя",
                value = state.username,
                onValueChange = { viewModel.onEvent(RegistrationEvent.UsernameChanged(it)) },
                enabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(spacer))
            EmailTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(RegistrationEvent.EmailChanged(it)) },
                enabled = !state.isLoading,
                onAction = KeyboardActions {
                    passwordFocusRequester.requestFocus()
                }
            )
            Spacer(modifier = Modifier.height(spacer))
            PasswordTextField(
                modifier = Modifier.focusRequester(passwordFocusRequester),
                value = state.password,
                onValueChange = { viewModel.onEvent(RegistrationEvent.PasswordChanged(it)) },
                enabled = !state.isLoading,
                passwordVisibility = state.passwordVisibility,
                onPasswordVisibilityChange = { viewModel.onEvent(RegistrationEvent.PasswordVisibilityChanged(it)) },
            )
            Spacer(modifier = Modifier.height(spacer))
            PasswordTextField(
                label = "Подтвердите пароль",
                modifier = Modifier.focusRequester(passwordFocusRequester),
                value = state.repeatedPassword,
                onValueChange = { viewModel.onEvent(RegistrationEvent.RepeatedPasswordChanged(it)) },
                enabled = !state.isLoading,
                passwordVisibility = state.passwordVisibility,
                onPasswordVisibilityChange = { viewModel.onEvent(RegistrationEvent.PasswordVisibilityChanged(it)) },
                onAction = KeyboardActions {
                    if (!state.isValid()) return@KeyboardActions
                    keyboardController?.hide()
                    viewModel.onEvent(RegistrationEvent.RegistrationConfirmed)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ActionButton(
                "Подтвердить",
                onClick = {
                    if (!state.isValid())
                        return@ActionButton

                    keyboardController?.hide()
                    viewModel.onEvent(RegistrationEvent.RegistrationConfirmed)
                },
                modifier = Modifier.width(240.dp)
            )
        }

        if (state.isLoading) {
            LoadingIndicator(modifier = Modifier.align(Alignment.Center))
        }

        viewModel.uiEvent.safeCollector(
            onMessageReceived = scaffoldState.snackbarHostState::showSnackbar,
            onSuccessCallback = {
                navigator.navigateEvent(NavigationEvent.NavigateUp)
                navigator.navigateEvent(NavigationEvent.CardArts)
            }
        )
    }
}