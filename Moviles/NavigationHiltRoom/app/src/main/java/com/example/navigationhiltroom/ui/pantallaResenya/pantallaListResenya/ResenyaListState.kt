package com.example.navigationhiltroom.ui.pantallaResenya.pantallaListResenya

import com.example.navigationhiltroom.domain.modelo.Resenya
import com.example.navigationhiltroom.ui.commons.UIEvent

data class ResenyaListState(
    val tituloSerie: String = "",
    val resenyas: List<Resenya> = emptyList(),
    val event: UIEvent? = null
)
