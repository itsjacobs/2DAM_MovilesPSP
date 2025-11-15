package com.example.navigationhiltroom.ui.pantallaResenya.pantallaListResenya

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationhiltroom.domain.usecases.resenyaUsecases.GetResenyasBySerieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResenyaListViewmodel @Inject constructor(
    private val getResenyasBySerieUseCase: GetResenyasBySerieUseCase
) : ViewModel() {

    private val _state = MutableLiveData(ResenyaListState())
    val state: LiveData<ResenyaListState> = _state

    fun cargarResenyas(tituloSerie: String) {
        viewModelScope.launch {
                val resenyas = getResenyasBySerieUseCase(tituloSerie)
                _state.value = _state.value?.copy(
                    tituloSerie = tituloSerie,
                    resenyas = resenyas,
                    event = null
                )
        }
    }

    fun limpiarEvento() {
        _state.value = _state.value?.copy(event = null)
    }
}