package com.example.navigationhiltroom.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.navigationhiltroom.data.commons.ConstantesDatabase

data class SerieConResenyas(
    @Embedded val serie: SerieEntity,
    @Relation(
        parentColumn = ConstantesDatabase.TITULO,
        entityColumn = ConstantesDatabase.TITULOSERIE
    )
    val reseñas: List<ResenyaEntity>
)
