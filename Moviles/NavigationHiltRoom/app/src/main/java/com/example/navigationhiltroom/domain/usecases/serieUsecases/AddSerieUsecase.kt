package com.example.navigationhiltroom.domain.usecases.serieUsecases

import com.example.navigationhiltroom.data.RepositorioSeries
import com.example.navigationhiltroom.domain.modelo.Serie
import javax.inject.Inject


class AddSerieUsecase @Inject constructor(private val repositorioSeries: RepositorioSeries) {

    suspend operator fun invoke (serie: Serie): Long {
        return repositorioSeries.save(serie)
    }
}