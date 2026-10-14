package dev.koalit.powersave.ejercicios.datastore.login

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.koalit.powersave.ejercicios.datastore.DatastorePreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    // 1. Expone el estado más actualizado a la UI
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state = _state.asStateFlow() // No la puede cambiar la UI

    private val preferences = DatastorePreferences(application)

    private var job: Job? = null

    // 2. Reacciona a los eventos del usuario
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        hasError = false
                    )
                }
            }
            is LoginEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        hasError = false
                    )
                }
            }
            LoginEvent.LoginClick -> {
                job?.cancel()
                job = viewModelScope.launch {
                    val email = state.value.email
                    val password = state.value.password
                    _state.update {
                        it.copy(
                            isLoading = true,
                            hasError = false
                        )
                    }
                    val isAuthenticated = authenticateUser(
                        email = email, password = password
                    )
                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = !isAuthenticated
                        )
                    }
                    if (isAuthenticated){
                        preferences.saveEmail(email)
                    }
                }
            }

            LoginEvent.CancelClick -> {
                job?.cancel()
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = false
                    )
                }
            }
        }
    }

    suspend fun authenticateUser(email: String, password: String): Boolean {
        delay(2.seconds)
        return password == "durini"
    }
}
