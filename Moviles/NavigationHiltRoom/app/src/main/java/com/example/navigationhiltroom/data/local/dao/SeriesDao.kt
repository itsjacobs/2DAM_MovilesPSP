package com.example.navigationhiltroom.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.navigationhiltroom.data.local.entities.SerieEntity

@Dao
interface SeriesDao {
    @Query("SELECT * FROM series")
    suspend fun getAllSeries(): List<SerieEntity>

    @Query("SELECT * FROM series WHERE titulo = :titulo")
    suspend fun getSerieByTitulo(titulo: String): SerieEntity?

    @Query("SELECT * FROM series WHERE isFavorito = 1")
    suspend fun getFavoriteSeries(): List<SerieEntity>

    @Query("UPDATE series SET isFavorito = :isFavorito WHERE titulo = :titulo")
    suspend fun updateFavoriteStatus(titulo: String, isFavorito: Boolean): Int

    @Insert
    suspend fun insertSerie(serie: SerieEntity): Long

    @Update
    suspend fun updateSerie(serie: SerieEntity): Int

    @Delete
    suspend fun deleteSerie(serie: SerieEntity): Int
}