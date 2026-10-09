package dev.koalit.powersave.ejercicios.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val authenticated: Boolean = false,
)
