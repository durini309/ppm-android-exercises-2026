package dev.koalit.powersave.ejercicios.corrutinas

sealed interface CorrutinasEvent {
    data class ModeChanged(val useThreadSleep: Boolean) : CorrutinasEvent
    data object StartCancelResumeClicked : CorrutinasEvent
}
