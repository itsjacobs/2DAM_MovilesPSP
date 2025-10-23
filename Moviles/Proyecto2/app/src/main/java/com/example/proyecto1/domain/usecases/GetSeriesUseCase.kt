package com.example.proyecto1.domain.usecases

import com.example.proyecto1.data.RepositorioSeries
import com.example.proyecto1.domain.modelo.Serie

class GetSeriesUseCase {
    operator fun invoke() : List<Serie>  = RepositorioSeries.getSeries()
}