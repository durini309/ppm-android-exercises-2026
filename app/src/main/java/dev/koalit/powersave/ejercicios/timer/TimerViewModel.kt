package dev.koalit.powersave.ejercicios.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {
    private val _state = MutableStateFlow(TimerScreenState())
    val state = _state.asStateFlow()

    // Guardamos la corrutina en un Job para poder pausarla (cancelarla) y reanudarla
    private var job: Job? = null

    fun onEvent(event: TimerScreenEvent) {
        when (event) {
            is TimerScreenEvent.OnTimeChange -> changeTime(event.value)
            TimerScreenEvent.StartClick -> start()
            TimerScreenEvent.PauseClick -> pause()
            TimerScreenEvent.ResumeClick -> resume()
            TimerScreenEvent.RestartClick -> restart()
        }
    }

    private fun changeTime(value: String) {
        // Solo se puede editar el tiempo cuando el timer no ha iniciado
        if (_state.value.timerStatus != TimerStatus.IDLE) return
        // Solo aceptamos dígitos
        if (value.all { it.isDigit() }) {
            _state.update { it.copy(timeStr = value) }
        }
    }

    private fun start() {
        val target = _state.value.timeStr.toIntOrNull() ?: return
        if (target <= 0) return
        _state.update { it.copy(counter = 0) }
        startJob(target)
    }

    private fun pause() {
        job?.cancel()
        _state.update { it.copy(timerStatus = TimerStatus.PAUSED) }
    }

    private fun resume() {
        val target = _state.value.timeStr.toIntOrNull() ?: return
        // Retoma desde el valor actual de counter
        startJob(target)
    }

    private fun restart() {
        job?.cancel()
        _state.update { it.copy(counter = 0, timeStr = "", timerStatus = TimerStatus.IDLE) }
    }

    private fun startJob(target: Int) {
        job?.cancel()
        job = viewModelScope.launch {
            _state.update { it.copy(timerStatus = TimerStatus.RUNNING) }
            while (_state.value.counter < target) {
                // Si se llama job.cancel() (pausa), la corrutina se detiene aquí
                delay(1.seconds)
                _state.update { it.copy(counter = it.counter + 1) }
            }
            _state.update { it.copy(timerStatus = TimerStatus.FINISHED) }
        }
    }
}
