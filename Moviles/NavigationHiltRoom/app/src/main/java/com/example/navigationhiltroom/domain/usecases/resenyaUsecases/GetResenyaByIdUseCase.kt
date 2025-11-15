package com.example.navigationhiltroom.domain.usecases.resenyaUsecases

import com.example.navigationhiltroom.data.RepositorioResenyas
import com.example.navigationhiltroom.domain.modelo.Resenya
import javax.inject.Inject

class GetResenyaByIdUseCase @Inject constructor(
    private val repositorio: RepositorioResenyas
) {
    suspend operator fun invoke(id: Int): Resenya? {
        return repositorio.getResenyaById(id)
    }
}

