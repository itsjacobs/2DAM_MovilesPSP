package com.example.navigationhiltroom.ui.pantallaSerie.pantallaFavoritasSerie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationhiltroom.domain.usecases.serieUsecases.GetFavoriteSeriesUseCase
import com.example.navigationhiltroom.domain.usecases.serieUsecases.UpdateFavoritoUseCase
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritasSeriesViewModel @Inject constructor(
    private val getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase,
    private val updateFavoritoUseCase: UpdateFavoritoUseCase
) : ViewModel() {

    var state: MutableLiveData<FavoritasSeriesState> = MutableLiveData()
        private set

    init {
        cargarSeriesFavoritas()
    }

    private fun cargarSeriesFavoritas() {
        viewModelScope.launch {
                val seriesFavoritas = getFavoriteSeriesUseCase()
                state.value = FavoritasSeriesState(series = seriesFavoritas)

        }
    }

    fun recargarSeriesFavoritas() {
        cargarSeriesFavoritas()
    }

    fun updateFavorito(titulo: String, isFavorito: Boolean) {
        viewModelScope.launch {
                val resultado = updateFavoritoUseCase(titulo, isFavorito)
                if (resultado) {
                    cargarSeriesFavoritas()
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
