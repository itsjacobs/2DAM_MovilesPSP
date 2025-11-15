package com.example.navigationhiltroom.domain.usecases.serieUsecases

import com.example.navigationhiltroom.data.RepositorioSeries
import com.example.navigationhiltroom.domain.modelo.Serie
import javax.inject.Inject

class GetFavoriteSeriesUseCase @Inject constructor(
    private val repositorio: RepositorioSeries
) {
    suspend operator fun invoke(): List<Serie> {
        return repositorio.getFavoriteSeries()
    }
}

