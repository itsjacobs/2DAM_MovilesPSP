package com.example.navigationhiltroom.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.navigationhiltroom.data.local.entities.ResenyaEntity
import com.example.navigationhiltroom.data.local.entities.SerieConResenyas

@Dao
interface ResenyasDao {

    @Query("SELECT * FROM resenyas")
    suspend fun getAllResenyas(): List<ResenyaEntity>

    @Query("SELECT * FROM resenyas WHERE tituloSerie = :tituloSerie")
    suspend fun getResenyasBySerie(tituloSerie: String): List<ResenyaEntity>

    @Transaction
    @Query("SELECT * FROM series WHERE titulo = :tituloSerie")
    suspend fun getSerieConResenyas(tituloSerie: String): SerieConResenyas?

    @Query("SELECT * FROM resenyas WHERE id = :id")
    suspend fun getResenyaById(id: Int): ResenyaEntity?

    @Insert
    suspend fun insertResenya(resenya: ResenyaEntity): Long

    @Update
    suspend fun updateResenya(resenya: ResenyaEntity): Int

    @Delete
    suspend fun deleteResenya(resenya: ResenyaEntity): Int
}
