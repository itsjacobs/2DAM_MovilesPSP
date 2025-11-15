package com.example.navigationhiltroom.ui.pantallaResenya.pantallaListSerieResenya

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationhiltroom.domain.usecases.serieUsecases.GetSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResenyaListSerieViewModel @Inject constructor(
    private val getSeriesUseCase: GetSeriesUseCase
) : ViewModel() {

    private val _state = MutableLiveData(ResenyaListSerieState())
    val state: LiveData<ResenyaListSerieState> = _state

    init {
        cargarSeries()
    }

    private fun cargarSeries() {
        viewModelScope.launch {
                val series = getSeriesUseCase()
                _state.value = _state.value?.copy(
                    series = series,
                    event = null
                )
        }
    }

    fun recargarSeries() {
        cargarSeries()
    }

    fun limpiarEvento() {
        _state.value = _state.value?.copy(event = null)
    }
}
