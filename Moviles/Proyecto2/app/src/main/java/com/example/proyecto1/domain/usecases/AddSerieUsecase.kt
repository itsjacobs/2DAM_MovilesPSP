package com.example.proyecto1.domain.usecases

import com.example.proyecto1.data.RepositorioSeries
import com.example.proyecto1.domain.modelo.Serie

class AddSerieUsecase {

    operator fun invoke (serie: Serie): Boolean {
        return RepositorioSeries.save(serie)
    }
}