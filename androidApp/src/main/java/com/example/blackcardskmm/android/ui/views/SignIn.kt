package com.example.blackcardskmm.android.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.components.ActionButton
import com.example.blackcardskmm.android.ui.components.EmailTextField
import com.example.blackcardskmm.android.ui.components.LoadingIndicator
import com.example.blackcardskmm.android.ui.components.PasswordTextField
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.components.auth.AuthComponent
import com.example.blackcardskmm.components.auth.AuthStore

@ExperimentalComposeUiApi
@Composable
fun SignIn(
    component: AuthComponent
) {
    val state by component.state.collectAsStateWithLifecycle()

    val passwordFocusRequester = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current

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
            Text(
                text = "BLACK CARDS",
                color = MaterialTheme.colors.primary,
                fontSize = 46.sp,
                fontFamily = mikadanFont
            )
            Spacer(modifier = Modifier.height(30.dp))
            EmailTextField(
                value = state.email,
                onValueChange = { component.onEvent(AuthStore.Intent.EmailChanged(it)) },
                enabled = !state.isLoading,
                onAction = KeyboardActions {
                    passwordFocusRequester.requestFocus()
                })
            Spacer(modifier = Modifier.height(8.dp))
            PasswordTextField(
                modifier = Modifier.focusRequester(passwordFocusRequester),
                value = state.password,
                onValueChange = { component.onEvent(AuthStore.Intent.PasswordChanged(it)) },
                enabled = !state.isLoading,
                passwordVisibility = state.passwordVisibility,
                onPasswordVisibilityChange = { component.onEvent(AuthStore.Intent.PasswordVisibilityChanged(it)) },
                onAction = KeyboardActions {
                    if (!state.isValid()) return@KeyboardActions
                    keyboardController?.hide()
                    component.onEvent(AuthStore.Intent.SignInConfirmed)
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            ActionButton(
                "Войти",
                onClick = {
                    if (!state.isValid()) return@ActionButton
                    keyboardController?.hide()
                    component.onEvent(AuthStore.Intent.SignInConfirmed)
                },
                modifier = Modifier.width(240.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ActionButton(
                "Регистрация",
                onClick = { component.onOutput(AuthComponent.Output.NavigateToRegister) },
                modifier = Modifier.width(240.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (state.isLoading) {
            LoadingIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (state.isSignedIn) {
            component.onOutput(AuthComponent.Output.NavigateToMain)
        }
    }
}