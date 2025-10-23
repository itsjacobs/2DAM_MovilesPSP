package com.example.proyecto1.ui.pantallaDetalles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto1.domain.usecases.VerSerieUsecase

class DetalleSerieViewModel (private val verSerieUsecase: VerSerieUsecase = VerSerieUsecase()) : ViewModel(){
    var state : MutableLiveData<DetalleSerieState> = MutableLiveData()
    private set;

    fun iniciar(titulo : String) {
        cargarDetalleSerie(titulo)
    }

    private fun cargarDetalleSerie(titulo : String) {
        val serie = verSerieUsecase.invoke(titulo)
        state.value = DetalleSerieState(serie = serie)
    }
}