package com.example.navigationhiltroom.domain.modelo

data class Resenya (
    val id: Int = 0,
    val tituloSerie: String = "",
    val contenido: String = "",
    val calificacion: Calificacion = Calificacion.VACIO,
)
enum class Calificacion {

    VACIO,
    UNA_ESTRELLA,
    DOS_ESTRELLAS,
    TRES_ESTRELLAS,
    CUATRO_ESTRELLAS,
    CINCO_ESTRELLAS
}


