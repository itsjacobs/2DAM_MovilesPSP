package com.example.navigationhiltroom.ui.pantallaResenya.pantallaListSerieResenya

import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.ui.commons.UIEvent

data class ResenyaListSerieState(
    val series: List<Serie> = emptyList(),
    val event: UIEvent? = null
)
