package com.example.proyecto1.ui.pantallaAdd

import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.ui.commons.UIEvent

data class AddState(
    val serie : Serie = Serie(),
    val botonGuardar : Boolean = false,
    val event: UIEvent? = null
)