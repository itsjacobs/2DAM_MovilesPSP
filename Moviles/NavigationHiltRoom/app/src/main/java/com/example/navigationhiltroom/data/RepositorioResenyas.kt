package com.example.navigationhiltroom.data

import com.example.navigationhiltroom.data.local.dao.ResenyasDao
import com.example.navigationhiltroom.data.local.entities.toResenya
import com.example.navigationhiltroom.data.local.entities.toResenyaEntity
import com.example.navigationhiltroom.domain.modelo.Resenya
import javax.inject.Inject

class RepositorioResenyas @Inject constructor(
    private val resenyasDao: ResenyasDao
) {

    suspend fun getResenyasBySerie(tituloSerie: String): List<Resenya> {
        val serieConResenyas = resenyasDao.getSerieConResenyas(tituloSerie)
        val resenyas = serieConResenyas?.reseñas?.map { it.toResenya() } ?: emptyList()
        resenyas.forEachIndexed { index, resenya ->
        }

        return resenyas
    }

    suspend fun getResenyaById(id: Int): Resenya? {
        return resenyasDao.getResenyaById(id)?.toResenya()
    }

    suspend fun insertResenya(resenya: Resenya): Long {
            val result = resenyasDao.insertResenya(resenya.toResenyaEntity())
            return result
    }

    suspend fun updateResenya(resenya: Resenya): Int {
        return resenyasDao.updateResenya(resenya.toResenyaEntity())
    }

    suspend fun deleteResenya(resenya: Resenya): Int {
        return resenyasDao.deleteResenya(resenya.toResenyaEntity())
    }
}
