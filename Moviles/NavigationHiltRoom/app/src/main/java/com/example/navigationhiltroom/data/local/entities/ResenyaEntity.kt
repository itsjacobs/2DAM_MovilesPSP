package com.example.navigationhiltroom.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.navigationhiltroom.data.commons.ConstantesDatabase
import com.example.navigationhiltroom.domain.modelo.Calificacion
import com.example.navigationhiltroom.domain.modelo.Resenya

@Entity(
    tableName = ConstantesDatabase.RESENYA_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = SerieEntity::class,
            parentColumns = arrayOf(ConstantesDatabase.TITULO),
            childColumns = arrayOf(ConstantesDatabase.TITULOSERIE),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = [ConstantesDatabase.TITULOSERIE])]
)
data class ResenyaEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tituloSerie: String = "",
    val contenido: String = "",
    val calificacion: Calificacion = Calificacion.VACIO,
)

fun ResenyaEntity.toResenya() = Resenya(
    id = this.id,
    tituloSerie = this.tituloSerie,
    contenido = this.contenido,
    calificacion = this.calificacion
)

fun Resenya.toResenyaEntity() = ResenyaEntity(
    id = this.id,
    tituloSerie = this.tituloSerie,
    contenido = this.contenido,
    calificacion = this.calificacion
)
