package com.example.proyecto1.ui.listadoSerie

import com.example.proyecto1.domain.modelo.Serie

data class ListadoSeriesState (
    val series : List<Serie> = emptyList(),
    val mensaje : String? = null
)