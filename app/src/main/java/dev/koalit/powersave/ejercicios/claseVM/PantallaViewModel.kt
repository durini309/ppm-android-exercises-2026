package dev.koalit.powersave.ejercicios.claseVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class PantallaViewModel: ViewModel() {
    private val _state = MutableStateFlow<PantallaVmState>(PantallaVmState())
    val state = _state.asStateFlow()

    fun changeState() {
        viewModelScope.launch {
            _state.emit(PantallaVmState(
                name = "Carlos",
                age = 21
            ))
            delay(1.seconds)
            _state.emit(PantallaVmState(
                name = "Durini",
                age = 23
            ))
            delay(1.seconds)
            _state.emit(PantallaVmState(
                name = "Serrano",
                age = 25
            ))
            delay(1.seconds)
            _state.emit(PantallaVmState(
                name = "Ana",
                age = 30
            ))
            delay(1.seconds)
        }
    }
}