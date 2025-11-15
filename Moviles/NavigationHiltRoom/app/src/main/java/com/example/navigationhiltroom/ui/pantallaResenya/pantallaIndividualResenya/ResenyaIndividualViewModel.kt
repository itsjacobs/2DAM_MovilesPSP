package com.example.navigationhiltroom.ui.pantallaResenya.pantallaIndividualResenya

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationhiltroom.domain.modelo.Calificacion
import com.example.navigationhiltroom.domain.usecases.resenyaUsecases.DeleteResenyaUseCase
import com.example.navigationhiltroom.domain.usecases.resenyaUsecases.GetResenyaByIdUseCase
import com.example.navigationhiltroom.domain.usecases.resenyaUsecases.UpdateResenyaUseCase
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResenyaIndividualViewModel @Inject constructor(
    private val getResenyaByIdUseCase: GetResenyaByIdUseCase,
    private val updateResenyaUseCase: UpdateResenyaUseCase,
    private val deleteResenyaUseCase: DeleteResenyaUseCase
) : ViewModel() {

    private val _state = MutableLiveData(ResenyaIndividualState())
    val state: LiveData<ResenyaIndividualState> = _state

    fun cargarResenya(resenyaId: Int) {
        viewModelScope.launch {
                val resenya = getResenyaByIdUseCase(resenyaId)
                _state.value = _state.value?.copy(
                    resenya = resenya,
                    event = null
                )
        }
    }

    fun updateResenya(contenido: String, calificacion: Calificacion) {
        viewModelScope.launch {
                val resenyaActual = _state.value?.resenya ?: return@launch

                if (contenido.isBlank()) {
                    _state.value = _state.value?.copy(
                        event = UIEvent.showSnackbar(Constantes.ERRORCONTENIDO)
                    )
                    return@launch
                }

                val resenyaActualizada = resenyaActual.copy(
                    contenido = contenido,
                    calificacion = calificacion
                )

                val result = updateResenyaUseCase(resenyaActualizada)

                if (result > 0) {
                    _state.value = _state.value?.copy(
                        resenya = resenyaActualizada,
                        event = UIEvent.Navigate(Constantes.RUTALISTADO)
                    )
                } else {
                    _state.value = _state.value?.copy(
                        event = UIEvent.showSnackbar(Constantes.ERRORACTULIZARRESEÑA)
                    )
                }
        }
    }

    fun deleteResenya() {
        viewModelScope.launch {
                val resenyaActual = _state.value?.resenya ?: return@launch

                val result = deleteResenyaUseCase(resenyaActual)

                if (result > 0) {
                    _state.value = _state.value?.copy(
                        event = UIEvent.Navigate(Constantes.RUTALISTADO)
                    )
                } else {
                    _state.value = _state.value?.copy(
                        event = UIEvent.showSnackbar(Constantes.ERRORBORRARRESEÑA)
                    )
                }
        }
    }

    fun limpiarEventos() {
        _state.value = _state.value?.copy(event = null)
    }
}
