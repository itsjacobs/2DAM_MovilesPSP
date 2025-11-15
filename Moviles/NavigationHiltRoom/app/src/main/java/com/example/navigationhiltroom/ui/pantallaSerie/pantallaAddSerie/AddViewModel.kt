package com.example.navigationhiltroom.ui.pantallaSerie.pantallaAddSerie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.domain.usecases.serieUsecases.AddSerieUsecase
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val addSerieUsecase: AddSerieUsecase
) : ViewModel() {
    private val _state: MutableLiveData<AddState> = MutableLiveData()
    val state: LiveData<AddState> get() = _state

    init {
        _state.value = AddState(
            serie = Serie()
        )
    }

    fun clickGuardarSerie(serie: Serie) {
        viewModelScope.launch {
            val rowId = addSerieUsecase.invoke(serie)
            if (rowId != -1L) {
                _state.value = state.value?.copy(
                    serie = serie,
                    event = UIEvent.Navigate(Constantes.RUTALISTADO)
                )
            } else {
                _state.value = state.value?.copy(
                    event = UIEvent.showSnackbar(Constantes.ERRORSERIEADD)
                )
            }
        }
    }

    fun limpiarEvento() {
        _state.value = _state.value?.copy(event = null)
    }
}
