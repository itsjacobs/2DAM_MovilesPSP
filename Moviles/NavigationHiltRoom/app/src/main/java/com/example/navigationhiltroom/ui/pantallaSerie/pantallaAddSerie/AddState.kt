package com.example.navigationhiltroom.ui.pantallaSerie.pantallaAddSerie

import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.ui.commons.UIEvent


data class AddState(
    val serie : Serie = Serie(),
    val botonGuardar : Boolean = false,
    val event: UIEvent? = null
)