package com.example.proyecto1.ui.pantallamain

import com.example.proyecto1.domain.modelo.Serie

data class MainState(
    val serie : Serie = Serie(),
    val botonLimpiar : Boolean = false,
    val botonActualizar : Boolean = false,
    val botonBorrar : Boolean = false,
    val botonGuardar : Boolean = false,
    val botonAnterior : Boolean = false,
    val botonSiguiente : Boolean = true,
    val Indice : Int = 0,
    val numeroPaginas : Int = 0,
    val mensaje : String? = null
)