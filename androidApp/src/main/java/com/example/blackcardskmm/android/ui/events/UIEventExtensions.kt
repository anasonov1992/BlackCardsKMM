package com.example.blackcardskmm.android.ui.events

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.blackcardskmm.android.ui.navigation.models.NavigationEvent
import com.example.blackcardskmm.android.util.UiText
import com.example.blackcardskmm.android.util.UiText.Companion.get
import com.example.blackcardskmm.events.UIEvent
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SharedFlow<UIEvent>.safeCollector(
    onNavigate: suspend (route: NavigationEvent) -> Unit = {},
    onDataReceived: suspend (data: Any) -> Unit = {},
    onMessageReceived: suspend (message: String) -> Unit = {},
    onSuccessCallback: () -> Unit = {}
) = apply {
    val context = LocalContext.current
    LaunchedEffect(this) {
        collect {
            when (it) {
                is UIEvent.ShowMessage -> onMessageReceived(it.uiText.get(context))
                is UIEvent.NavigateTo -> onNavigate(it.navigationEvent)
                is UIEvent.SendData -> onDataReceived(it.data)
                is UIEvent.Success -> onSuccessCallback()
            }
        }
    }
}

fun navigate(
    navigationEvent: NavigationEvent
): UIEvent.NavigateTo = UIEvent.NavigateTo(navigationEvent)

fun showMessage(
    value: String,
): UIEvent.ShowMessage = UIEvent.ShowMessage(UiText.StringText(value))

fun showMessage(
    @StringRes res: Int
): UIEvent.ShowMessage = UIEvent.ShowMessage(UiText.ResourceText(res))

fun success(): UIEvent.Success = UIEvent.Success