package dev.koalit.powersave.ejercicios.login

sealed interface LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent
    data object LoginClick : LoginEvent
    data object CancelClick: LoginEvent
}
