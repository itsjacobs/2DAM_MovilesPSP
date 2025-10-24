package com.example.proyecto1.ui.pantallaDetalles

import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.ui.commons.UIEvent

data class DetalleSerieState (
    val serie : Serie = Serie(),
    val botonVolver : Boolean = false,
    val botonBorrar : Boolean = false,
    val botonActualizar : Boolean = false,
    val event: UIEvent? = null
)