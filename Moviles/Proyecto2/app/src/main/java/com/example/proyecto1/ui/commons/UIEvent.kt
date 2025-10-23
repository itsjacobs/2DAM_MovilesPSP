package com.example.proyecto1.ui.commons

sealed interface UIEvent {
    data class showSnackbar(val message: String) : UIEvent
}