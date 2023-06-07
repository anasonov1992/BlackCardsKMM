package com.example.blackcardskmm.android.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
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
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.components.*
import com.example.blackcardskmm.components.register.RegisterComponent
import com.example.blackcardskmm.components.register.RegisterStore

@ExperimentalComposeUiApi
@Composable
fun Register(
    component: RegisterComponent
) {
    val state by component.state.collectAsStateWithLifecycle()

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
                onValueChange = { component.onEvent(RegisterStore.Intent.UsernameChanged(it)) },
                enabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(spacer))
            EmailTextField(
                value = state.email,
                onValueChange = { component.onEvent(RegisterStore.Intent.EmailChanged(it)) },
                enabled = !state.isLoading,
                onAction = KeyboardActions {
                    passwordFocusRequester.requestFocus()
                }
            )
            Spacer(modifier = Modifier.height(spacer))
            PasswordTextField(
                modifier = Modifier.focusRequester(passwordFocusRequester),
                value = state.password,
                onValueChange = { component.onEvent(RegisterStore.Intent.PasswordChanged(it)) },
                enabled = !state.isLoading,
                passwordVisibility = state.passwordVisibility,
                onPasswordVisibilityChange = { component.onEvent(RegisterStore.Intent.PasswordVisibilityChanged(it)) },
            )
            Spacer(modifier = Modifier.height(spacer))
            PasswordTextField(
                label = "Подтвердите пароль",
                modifier = Modifier.focusRequester(passwordFocusRequester),
                value = state.repeatedPassword,
                onValueChange = { component.onEvent(RegisterStore.Intent.RepeatedPasswordChanged(it))  },
                enabled = !state.isLoading,
                passwordVisibility = state.passwordVisibility,
                onPasswordVisibilityChange = { component.onEvent(RegisterStore.Intent.PasswordVisibilityChanged(it)) },
                onAction = KeyboardActions {
                    if (!state.isValid()) return@KeyboardActions
                    keyboardController?.hide()
                    component.onEvent(RegisterStore.Intent.RegistrationConfirmed)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ActionButton(
                "Подтвердить",
                onClick = {
                    if (!state.isValid()) return@ActionButton
                    keyboardController?.hide()
                    component.onEvent(RegisterStore.Intent.RegistrationConfirmed)
                },
                modifier = Modifier.width(240.dp)
            )
        }

        if (state.isLoading) {
            LoadingIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}