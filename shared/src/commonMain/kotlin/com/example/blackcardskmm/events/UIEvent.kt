package com.example.blackcardskmm.events

sealed class UIEvent {
    data class ShowMessage(val uiText: UIText) : UIEvent()
    data class NavigateTo(val navigationEvent: NavigationEvent) : UIEvent()
    data class SendData(val data: Any) : UIEvent()
    object Success : UIEvent()
}