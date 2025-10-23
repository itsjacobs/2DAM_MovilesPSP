package com.example.proyecto1.ui.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.domain.usecases.ActualizarSerieUsecase
import com.example.proyecto1.domain.usecases.AddSerieUsecase
import com.example.proyecto1.domain.usecases.DeleteSerieUsecase
import com.example.proyecto1.domain.usecases.SizeSerieUsecase
import com.example.proyecto1.domain.usecases.VerSerieUsecase

class MainViewModel : ViewModel() {
    private val _state : MutableLiveData<MainState> = MutableLiveData()
    val state : LiveData<MainState> get() = _state


    init {
        val totalSeries = SizeSerieUsecase().invoke()
        _state.value = MainState(
            serie = Serie(),
            Indice = 0,
            numeroPaginas = totalSeries
        )
    }

    fun Botones(){
        val indice = _state.value?.Indice ?: 0
        val total = _state.value?.numeroPaginas ?: 0

        if (indice <= 0) {
            _state.value = _state.value?.copy(botonSiguiente = true, botonAnterior = false)
            return
        }
        if (indice >= total) {
            _state.value = _state.value?.copy(botonSiguiente = false, botonAnterior = true)
            return
        }
        _state.value = _state.value?.copy(botonSiguiente = true, botonAnterior=true)
    }
    fun clickGuardarSerie(serie: Serie){
        val paginasActuales = _state.value?.numeroPaginas ?: 0
        if (AddSerieUsecase().invoke(serie)){
           _state.value = state.value?.copy(serie, mensaje = "Serie añadida correctamente", numeroPaginas = paginasActuales + 1 )
        }else{
           _state.value = state.value?.copy(mensaje = "No se ha podido añadir la serie")
       }
        Botones()
    }

    fun limpiarMensaje() {
        _state.value = state.value?.copy(mensaje = null)
    }
    fun pasarPagina(){
        val indice = _state.value?.Indice ?: 0
        val serie = VerSerieUsecase().invoke(indice)

        _state.value = state.value?.copy(serie = serie, Indice = indice + 1)
        Botones()
    }

    fun volverPagina() {
        val indiceActual = _state.value?.Indice ?: 0
        val nuevoIndice = indiceActual - 1

        if (nuevoIndice >= 0) {
            if (nuevoIndice == 0) {
                _state.value = _state.value?.copy(
                    serie = Serie(),
                    Indice = 0,
                )
            } else {
                val serieAnterior = VerSerieUsecase().invoke(nuevoIndice - 1)
                _state.value = _state.value?.copy(
                    serie = serieAnterior,
                    Indice = nuevoIndice,
                )
            }
        }
        Botones()
    }
    fun clickBorrar(serie: Serie) {
        if (DeleteSerieUsecase().invoke(serie)) {
            val totalSeriesAhora = SizeSerieUsecase().invoke()

            _state.value = _state.value?.copy(
                serie = Serie(),
                Indice = 0,
                numeroPaginas = totalSeriesAhora,
                mensaje = "Serie borrada correctamente"
            )
        } else {
            _state.value = _state.value?.copy(mensaje = "No se pudo borrar la serie")
        }
        Botones()
    }

    fun clickActualizarSerie(serie: Serie) {
        if (ActualizarSerieUsecase().invoke(serie)) {
            _state.value = _state.value?.copy(
                serie = serie,
                mensaje = "Serie actualizada correctamente"
            )
        } else {
            _state.value = _state.value?.copy(mensaje = "No se pudo actualizar la serie")
        }
        Botones()
    }
}
class MainViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
