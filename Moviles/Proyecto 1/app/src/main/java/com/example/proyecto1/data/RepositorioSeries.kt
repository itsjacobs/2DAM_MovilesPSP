package com.example.proyecto1.data

import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.domain.modelo.Tipo

object RepositorioSeries {

    private val series = mutableListOf<Serie>()

    init{
        series.add(Serie("prueba1","prueba1",5,1970,"23/07/1990","muy buena",true, Tipo.EnEmision))
    }

    fun getSerie(id:Int) = series[id]
    fun save(serie: Serie) = series.add(serie)
    fun size() = series.size
    fun delete(serie: Serie)= series.remove(serie)
    fun update(serieActualizada : Serie) : Boolean{
        val indice = series.indexOfFirst { serieDelaLista -> serieDelaLista.textoTitulo == serieActualizada.textoTitulo }
        return if (indice != -1) {
            series[indice] = serieActualizada
            true
        } else {
            false
        }
    }
}