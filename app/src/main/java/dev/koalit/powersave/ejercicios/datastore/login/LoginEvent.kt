package dev.koalit.powersave.ejercicios.datastore.login

sealed interface LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent
    data object LoginClick : LoginEvent
    data object CancelClick: LoginEvent
}
