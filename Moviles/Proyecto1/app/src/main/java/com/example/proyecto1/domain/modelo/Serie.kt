package com.example.proyecto1.domain.modelo

import androidx.recyclerview.widget.DiffUtil

data class Serie (
    val textoTitulo : String = "",
    val textoGenero : String = "",
    val numeroTemporadas : Int = 0,
    val anoEstreno : Int = 0,
    val ultimaEmision : String = "",
    val textoSinopsis : String = "",
    val isAceptado : Boolean = false,
    val estadoSerie : Tipo = Tipo.VACIO
) {
    companion object {
        fun diffCallback() = object : DiffUtil.ItemCallback<Serie>() {
            override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean {
                return oldItem.textoTitulo == newItem.textoTitulo
            }

            override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean {
                return oldItem == newItem
            }
        }
    }
}

enum class Tipo{
    VACIO,
    EnEmision,
    Finalizada,
    Proximamtente
}