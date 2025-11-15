package com.example.navigationhiltroom.ui.pantallaSerie.pantallaDetailSerie

import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.ui.commons.UIEvent


data class DetalleSerieState (
    val serie : Serie = Serie(),
    val botonVolver : Boolean = false,
    val botonBorrar : Boolean = false,
    val botonActualizar : Boolean = false,
    val event: UIEvent? = null
)