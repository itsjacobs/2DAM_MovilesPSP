package com.example.proyecto1.ui.pantallaDetalles

import com.example.proyecto1.domain.modelo.Serie

data class DetalleSerieState (
    val serie : Serie = Serie(),
    val botonVolver : Boolean = false,
    val botonBorrar : Boolean = false,
    val botonActualizar : Boolean = false,
    val mensaje : String? = null
)