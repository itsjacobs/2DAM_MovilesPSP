package com.example.navigationhiltroom.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.navigationhiltroom.data.commons.ConstantesDatabase
import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.domain.modelo.Tipo

@Entity(
    tableName = ConstantesDatabase.SERIE_TABLE,
)
data class SerieEntity (
    @PrimaryKey
    val titulo : String,
    val textoGenero : String,
    val numeroTemporadas : Int,
    val anoEstreno : Int,
    val ultimaEmision : String,
    val textoSinopsis : String,
    val isAceptado : Boolean,
    val isFavorito : Boolean,
    val estadoSerie : Tipo,
)


fun SerieEntity.toSerie() = Serie(
    titulo = this.titulo,
    textoGenero = this.textoGenero,
    numeroTemporadas = this.numeroTemporadas,
    anoEstreno = this.anoEstreno,
    ultimaEmision = this.ultimaEmision,
    textoSinopsis = this.textoSinopsis,
    isAceptado = this.isAceptado,
    isFavorito = this.isFavorito,
    estadoSerie = this.estadoSerie,
)
fun Serie.toSerieEntity() = SerieEntity(
    titulo = this.titulo,
    textoGenero = this.textoGenero,
    numeroTemporadas = this.numeroTemporadas,
    anoEstreno = this.anoEstreno,
    ultimaEmision = this.ultimaEmision,
    textoSinopsis = this.textoSinopsis,
    isAceptado = this.isAceptado,
    estadoSerie = this.estadoSerie,
    isFavorito = this.isFavorito
)