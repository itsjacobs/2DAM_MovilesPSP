package com.example.navigationhiltroom.ui.pantallaSerie.pantallaListadoSerie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationhiltroom.domain.usecases.serieUsecases.GetSeriesUseCase
import com.example.navigationhiltroom.domain.usecases.serieUsecases.UpdateFavoritoUseCase
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListadoSeriesViewModel @Inject constructor(
    private val getSeriesUseCase: GetSeriesUseCase,
    private val updateFavoritoUseCase: UpdateFavoritoUseCase
) : ViewModel(){

    var state : MutableLiveData<ListadoSeriesState> = MutableLiveData()
    private set

    init {
        cargarSeries()
    }

    private fun cargarSeries() {
        viewModelScope.launch {
                val series = getSeriesUseCase()
                state.value = ListadoSeriesState(series = series)
        }
    }

    fun recargarSeries() {
        cargarSeries()
    }

    fun updateFavorito(titulo: String, isFavorito: Boolean) {
        viewModelScope.launch {
                val resultado = updateFavoritoUseCase(titulo, isFavorito)
                if (resultado) {
                    cargarSeries()
                    state.value = state.value?.copy(
                        event = UIEvent.showSnackbar(Constantes.FAVORITOACTUALIZADO)
                    )
                } else {
                    state.value = state.value?.copy(
                        event = UIEvent.showSnackbar(Constantes.ERRORACTUALIZARFAVORITO)
                    )
                }
        }
    }

    fun limpiarEvento() {
        state.value = state.value?.copy(event = null)
    }
}
