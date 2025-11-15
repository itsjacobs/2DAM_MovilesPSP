package com.example.navigationhiltroom.ui.pantallaResenya.pantallaAddResenya

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationhiltroom.domain.modelo.Calificacion
import com.example.navigationhiltroom.domain.modelo.Resenya
import com.example.navigationhiltroom.domain.usecases.resenyaUsecases.AddResenyaUseCase
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResenyaAddViewmodel @Inject constructor(
    private val addResenyaUseCase: AddResenyaUseCase
) : ViewModel() {

    private val _state = MutableLiveData(ResenyaAddState())
    val state: LiveData<ResenyaAddState> = _state

    fun cargarTituloSerie(tituloSerie: String) {
        _state.value = _state.value?.copy(tituloSerie = tituloSerie)
    }

    fun addResenya(contenido: String, calificacion: Calificacion) {
        viewModelScope.launch {
                val tituloSerie = _state.value?.tituloSerie ?: ""

                if (contenido.isBlank()) {
                    _state.value = _state.value?.copy(
                        event = UIEvent.showSnackbar(Constantes.ERRORCONTENIDO)
                    )
                    return@launch
                }

                val resenya = Resenya(
                    tituloSerie = tituloSerie,
                    contenido = contenido,
                    calificacion = calificacion
                )


                val result = addResenyaUseCase(resenya)

                if (result > 0) {
                    _state.value = _state.value?.copy(
                        event = UIEvent.Navigate(Constantes.RUTALISTADO)
                    )
                } else {
                    _state.value = _state.value?.copy(
                        event = UIEvent.showSnackbar(Constantes.ERRORRESEÑAADD)
                    )
                }
        }
    }

    fun limpiarEvento() {
        _state.value = _state.value?.copy(event = null)
    }
}