package com.example.navigationhiltroom.domain.usecases.serieUsecases

import com.example.navigationhiltroom.data.RepositorioSeries
import javax.inject.Inject

class UpdateFavoritoUseCase @Inject constructor(
    private val repositorio: RepositorioSeries
) {
    suspend operator fun invoke(titulo: String, isFavorito: Boolean): Boolean {
        return repositorio.updateFavorito(titulo, isFavorito)
    }
}
