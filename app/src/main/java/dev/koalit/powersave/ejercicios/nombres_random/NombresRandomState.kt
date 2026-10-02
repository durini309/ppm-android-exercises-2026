package dev.koalit.powersave.ejercicios.nombres_random

data class NombresRandomState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val number: String = "",
    val name: String = "",
)
