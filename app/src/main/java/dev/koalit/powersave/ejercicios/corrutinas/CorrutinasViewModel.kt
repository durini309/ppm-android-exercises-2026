package dev.koalit.powersave.ejercicios.corrutinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class CorrutinasViewModel: ViewModel() {
    private val _state = MutableStateFlow<CorrutinasState>(CorrutinasState())
    val state = _state.asStateFlow()

    private val people = listOf(
        "Carlos" to 21,
        "Durini" to 23,
        "Serrano" to 25,
        "Ana" to 30,
        "Luis" to 35,
        "María" to 40,
        "Pedro" to 45,
        "Sofía" to 50
    )

    // Guardamos la corrutina en un Job para poder cancelarla después
    private var job: Job? = null

    // Índice de la siguiente persona a mostrar (para poder reanudar donde nos quedamos)
    private var nextIndex = 0

    fun onEvent(event: CorrutinasEvent) {
        when (event) {
            is CorrutinasEvent.ModeChanged -> changeMode(event.useThreadSleep)
            CorrutinasEvent.StartCancelResumeClicked -> startCancelResume()
        }
    }

    private fun changeMode(useThreadSleep: Boolean) {
        // Mientras corre no se puede cambiar el modo
        if (_state.value.status == JobStatus.RUNNING) return
        _state.update { it.copy(useThreadSleep = useThreadSleep) }
    }

    private fun startCancelResume() {
        when (_state.value.status) {
            JobStatus.IDLE, JobStatus.FINISHED -> {
                nextIndex = 0
                startJob()
            }
            JobStatus.RUNNING -> {
                job?.cancel()
                _state.update { it.copy(status = JobStatus.CANCELLED) }
            }
            JobStatus.CANCELLED -> startJob()
        }
    }

    private fun startJob() {
        job?.cancel()
        val useThreadSleep = _state.value.useThreadSleep
        job = viewModelScope.launch {
            _state.update { it.copy(status = JobStatus.RUNNING) }
            for (index in nextIndex until people.size) {
                val (name, age) = people[index]
                _state.update { it.copy(name = name, age = age, tick = index + 1) }
                nextIndex = index + 1
                if (useThreadSleep) {
                    // BLOQUEA el hilo principal (viewModelScope corre en Main).
                    // La UI se congela: no se puede tocar nada y solo se ve el último valor al terminar.
                    Thread.sleep(1000)
                } else {
                    // SUSPENDE la corrutina sin bloquear el hilo: la UI sigue respondiendo.
                    // Si se llama job.cancel(), la corrutina se detiene aquí.
                    delay(1.seconds)
                }
            }
            nextIndex = 0
            _state.update { it.copy(status = JobStatus.FINISHED) }
        }
    }
}
