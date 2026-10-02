package dev.koalit.powersave.ejercicios.nombres_random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.koalit.powersave.ejercicios.claseVM.PantallaVmState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class NombresRandomViewModel : ViewModel() {
    private val _state = MutableStateFlow(NombresRandomState())
    val state = _state.asStateFlow()

    init {
        changeState()
    }

    fun changeState() {
        viewModelScope.launch {
            // Cambiamos estado a "Está cargando"
            _state.value = _state.value.copy(
                isLoading = true
            )
            delay(2.seconds)
            val numRand = Random.nextInt(0, 50)

            // Si es par, es exitoso
            if (numRand % 2 == 0) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasError = false,
                    name = "Joaquin",
                    number = numRand.toString()
                )
            } else {
                // si es impar, error
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasError = true,
                    number = numRand.toString()
                )
            }
        }
    }
}