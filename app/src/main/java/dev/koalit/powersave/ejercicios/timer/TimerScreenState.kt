package dev.koalit.powersave.ejercicios.timer

enum class TimerStatus {
    IDLE,
    RUNNING,
    PAUSED,
    FINISHED
}

data class TimerScreenState(
    val counter: Int = 0,
    val timeStr: String = "",
    val timerStatus: TimerStatus = TimerStatus.IDLE
)
