package com.example.proyecto1.ui.pantallaDetalles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.domain.usecases.ActualizarSerieUsecase
import com.example.proyecto1.domain.usecases.DeleteSerieUsecase
import com.example.proyecto1.domain.usecases.GetSeriesUseCase
import com.example.proyecto1.ui.commons.UIEvent

class DetalleSerieViewModel (
    private val getSeriesUseCase: GetSeriesUseCase,
    private val DeleteSerieUsecase: DeleteSerieUsecase,
    private val ActualizarSerieUsecase: ActualizarSerieUsecase,
    ) : ViewModel(){
    private val _uiState: MutableLiveData<DetalleSerieState> = MutableLiveData(DetalleSerieState())
    val uiState: LiveData<DetalleSerieState> get() = _uiState

    fun getSeries(titulo: String) {
        val series = getSeriesUseCase.invoke()

        val serie = series.find { it.titulo == titulo }

        if (serie == null){
            _uiState.value = _uiState.value?.copy(event = UIEvent.showSnackbar("Error al cargar la serie"))

        }else{
            _uiState.value = _uiState.value?.copy(serie = serie)
        }

    }

    fun clickBorrar(serie: Serie){
        if (DeleteSerieUsecase.invoke(serie)) {
            _uiState.value = _uiState.value?.copy(
                serie = Serie(),
                event = UIEvent.Navigate("listado")
            )
        } else {
            _uiState.value = _uiState.value?.copy(event = UIEvent.showSnackbar("Error al borrar la serie"))
        }
    }

    fun clickActualizarSerie(serie: Serie) {
        if (ActualizarSerieUsecase.invoke(serie)) {
            _uiState.value = _uiState.value?.copy(
                serie = serie,
                event = UIEvent.Navigate("listado")
            )
        } else {
            _uiState.value = _uiState.value?.copy(event = UIEvent.showSnackbar("Error al actualizar la serie"))
        }
    }
    fun limpiarEvento() {
        _uiState.value = _uiState.value?.copy(event = null)
    }
}
class DetalleViewModelFactory(
    private val getSeriesUseCase: GetSeriesUseCase,
    private val deletePersonaUseCase: DeleteSerieUsecase,
    private val actualizarSerieUsecase: ActualizarSerieUsecase,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleSerieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetalleSerieViewModel(
                getSeriesUseCase,
                deletePersonaUseCase,
                actualizarSerieUsecase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}