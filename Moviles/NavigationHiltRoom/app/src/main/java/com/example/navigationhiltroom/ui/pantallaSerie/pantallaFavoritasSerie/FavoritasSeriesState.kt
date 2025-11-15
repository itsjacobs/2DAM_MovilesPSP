package com.example.navigationhiltroom.ui.pantallaSerie.pantallaFavoritasSerie

import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.ui.commons.UIEvent

data class FavoritasSeriesState(
    val series: List<Serie> = emptyList(),
    val event: UIEvent? = null
)
