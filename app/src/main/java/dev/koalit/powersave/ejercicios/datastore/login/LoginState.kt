package dev.koalit.powersave.ejercicios.datastore.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
)
