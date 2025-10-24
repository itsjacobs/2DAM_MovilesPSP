package com.example.proyecto1.ui.pantallaAdd

import com.example.proyecto1.domain.modelo.Serie

data class AddState(
    val serie : Serie = Serie(),
    val botonLimpiar : Boolean = false,
    val botonGuardar : Boolean = false,
    val mensaje : String? = null
)