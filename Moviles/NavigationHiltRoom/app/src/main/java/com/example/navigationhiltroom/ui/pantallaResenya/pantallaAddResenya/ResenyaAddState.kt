package com.example.navigationhiltroom.ui.pantallaResenya.pantallaAddResenya

import com.example.navigationhiltroom.ui.commons.UIEvent

data class ResenyaAddState(
    val tituloSerie: String = "",
    val contenido: String = "",
    val event: UIEvent? = null
)
