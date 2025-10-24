package com.example.proyecto1.ui.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.domain.usecases.AddSerieUsecase

class MainViewModel : ViewModel() {
    private val _state : MutableLiveData<MainState> = MutableLiveData()
    val state : LiveData<MainState> get() = _state


    init {
        _state.value = MainState(
            serie = Serie())
    }

    fun clickGuardarSerie(serie: Serie){
        if (AddSerieUsecase().invoke(serie)){
           _state.value = state.value?.copy(serie, mensaje = "Serie añadida correctamente")
        }else{
           _state.value = state.value?.copy(mensaje = "No se ha podido añadir la serie")
       }

    }
    fun limpiarMensaje() {
        _state.value = state.value?.copy(mensaje = null)
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
