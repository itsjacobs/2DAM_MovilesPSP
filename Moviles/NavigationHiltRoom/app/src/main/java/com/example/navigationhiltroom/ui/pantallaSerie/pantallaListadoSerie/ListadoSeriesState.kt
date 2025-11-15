package com.example.navigationhiltroom.ui.pantallaSerie.pantallaListadoSerie

import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.ui.commons.UIEvent

data class ListadoSeriesState (
    val series : List<Serie> = emptyList(),
    val event: UIEvent? = null
)