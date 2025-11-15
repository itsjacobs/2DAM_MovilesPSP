package com.example.navigationhiltroom.ui.pantallaResenya.pantallaIndividualResenya

import com.example.navigationhiltroom.domain.modelo.Resenya
import com.example.navigationhiltroom.ui.commons.UIEvent

data class ResenyaIndividualState(
    val resenya: Resenya? = null,
    val event: UIEvent? = null
)
