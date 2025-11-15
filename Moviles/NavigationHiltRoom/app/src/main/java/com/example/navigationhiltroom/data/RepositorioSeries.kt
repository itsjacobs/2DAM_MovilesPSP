package com.example.navigationhiltroom.data

import com.example.navigationhiltroom.data.local.dao.SeriesDao
import com.example.navigationhiltroom.data.local.entities.toSerie
import com.example.navigationhiltroom.data.local.entities.toSerieEntity
import com.example.navigationhiltroom.domain.modelo.Serie
import javax.inject.Inject

class RepositorioSeries @Inject constructor(private val serieDao: SeriesDao){

    suspend fun save(serie: Serie): Long {
        val result = serieDao.insertSerie(serie.toSerieEntity())
        return result
    }

    suspend fun delete(serie: Serie): Boolean {
        val serieEntity = serieDao.getSerieByTitulo(serie.titulo)
        return if (serieEntity != null) {
            val rowsDeleted = serieDao.deleteSerie(serieEntity)
            rowsDeleted > 0
        } else {
            false
        }
    }

    suspend fun update(serieActualizada: Serie): Boolean {
        val rowsUpdated = serieDao.updateSerie(serieActualizada.toSerieEntity())
        return rowsUpdated > 0
    }

    suspend fun getSeries(): List<Serie> = serieDao.getAllSeries().map { it.toSerie() }

    suspend fun getSerie(titulo: String): Serie? = serieDao.getSerieByTitulo(titulo)?.toSerie()

    suspend fun getFavoriteSeries(): List<Serie> = serieDao.getFavoriteSeries().map { it.toSerie() }

    suspend fun updateFavorito(titulo: String, isFavorito: Boolean): Boolean {
        val rowsUpdated = serieDao.updateFavoriteStatus(titulo, isFavorito)
        return rowsUpdated > 0
    }
}