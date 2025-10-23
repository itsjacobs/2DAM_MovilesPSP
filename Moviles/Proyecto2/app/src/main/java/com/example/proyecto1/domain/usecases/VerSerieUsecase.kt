package com.example.proyecto1.domain.usecases

import com.example.proyecto1.data.RepositorioSeries
import com.example.proyecto1.domain.modelo.Serie

class VerSerieUsecase {

    operator fun invoke(titulo : String): Serie = RepositorioSeries.getSerie(titulo);
}