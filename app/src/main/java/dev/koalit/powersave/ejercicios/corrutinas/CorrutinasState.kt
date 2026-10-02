package dev.koalit.powersave.ejercicios.corrutinas

enum class JobStatus {
    IDLE,
    RUNNING,
    CANCELLED,
    FINISHED
}

data class CorrutinasState(
    val name: String = "Juan",
    val age: Int = 18,
    val tick: Int = 0,
    val status: JobStatus = JobStatus.IDLE,
    val useThreadSleep: Boolean = false
)
