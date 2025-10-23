package com.example.proyecto1.ui.listadoSerie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.domain.usecases.DeleteSerieUsecase
import com.example.proyecto1.domain.usecases.GetSeriesUseCase

class ListadoSeriesViewModel(private val getSeriesUseCase: GetSeriesUseCase = GetSeriesUseCase()) : ViewModel(){

    var state : MutableLiveData<ListadoSeriesState> = MutableLiveData()
    private set

    init {
        cargarSeries()
    }
    private fun cargarSeries() {
        val series = getSeriesUseCase()
        state.value = ListadoSeriesState(series = series)
    }

    fun limpiarMensaje() {
        state.value = state.value?.copy(mensaje = null)
    }


    }
