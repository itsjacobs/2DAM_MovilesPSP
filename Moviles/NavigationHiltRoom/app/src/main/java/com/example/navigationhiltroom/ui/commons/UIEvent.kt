package com.example.navigationhiltroom.ui.commons

sealed interface UIEvent {
    data class showSnackbar(val message: String) : UIEvent
    data class Navigate(val route: String) : UIEvent
}