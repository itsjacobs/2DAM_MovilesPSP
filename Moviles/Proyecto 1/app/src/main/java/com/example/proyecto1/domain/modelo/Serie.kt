package com.example.proyecto1.domain.modelo

data class Serie (
    val textoTitulo : String = "",
    val textoGenero : String = "",
    val numeroTemporadas : Int = 0,
    val anoEstreno : Int = 0,
    val ultimaEmision : String = "",
    val textoSinopsis : String = "",
    val isAceptado : Boolean = false,
    val estadoSerie : Tipo = Tipo.VACIO
    )

enum class Tipo{
    VACIO,
    EnEmision,
    Finalizada,
    Proximamtente
}