package com.example.navigationhiltroom.ui.pantallaSerie.pantallaDetailSerie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.domain.usecases.serieUsecases.ActualizarSerieUsecase
import com.example.navigationhiltroom.domain.usecases.serieUsecases.DeleteSerieUsecase
import com.example.navigationhiltroom.domain.usecases.serieUsecases.GetSerieUseCase
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetalleSerieViewModel @Inject constructor(
    private val getSerieUseCase: GetSerieUseCase,
    private val deleteSerieUsecase: DeleteSerieUsecase,
    private val actualizarSerieUsecase: ActualizarSerieUsecase
) : ViewModel() {
    private val _uiState: MutableLiveData<DetalleSerieState> = MutableLiveData(DetalleSerieState())
    val uiState: LiveData<DetalleSerieState> get() = _uiState

    fun getSeries(titulo: String) {
        viewModelScope.launch {
            val serie = getSerieUseCase.invoke(titulo)

            if (serie == null) {
                _uiState.value = _uiState.value?.copy(
                    event = UIEvent.showSnackbar(Constantes.ERRORCARGARSERIE)
                )
            } else {
                _uiState.value = _uiState.value?.copy(serie = serie)
            }
        }
    }

    fun clickBorrar(serie: Serie) {
        viewModelScope.launch {
            if (deleteSerieUsecase.invoke(serie)) {
                _uiState.value = _uiState.value?.copy(
                    serie = Serie(),
                    event = UIEvent.Navigate(Constantes.RUTALISTADO)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    event = UIEvent.showSnackbar(Constantes.ERRORBORRARSERIE)
                )
            }
        }
    }

    fun clickActualizarSerie(serie: Serie) {
        viewModelScope.launch {
            if (actualizarSerieUsecase.invoke(serie)) {
                _uiState.value = _uiState.value?.copy(
                    serie = serie,
                    event = UIEvent.Navigate(Constantes.RUTALISTADO)
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    event = UIEvent.showSnackbar(Constantes.ERRORACTUALIZARSERIE)
                )
            }
        }
    }

    fun limpiarEvento() {
        _uiState.value = _uiState.value?.copy(event = null)
    }
}