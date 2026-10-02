package dev.koalit.powersave.ejercicios.timer

sealed interface TimerScreenEvent {
    data object StartClick : TimerScreenEvent
    data class OnTimeChange(val value: String) : TimerScreenEvent
    data object PauseClick : TimerScreenEvent
    data object RestartClick : TimerScreenEvent
    data object ResumeClick : TimerScreenEvent
}
