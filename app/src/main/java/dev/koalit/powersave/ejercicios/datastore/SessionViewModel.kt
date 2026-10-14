package dev.koalit.powersave.ejercicios.datastore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SessionViewModel(application: Application): AndroidViewModel(application) {

    private val sessionPreferences = DatastorePreferences(context = application)

    // Tenemos que convertir de Flow<Boolean> a StateFlow<Boolean>
    val isLogged = sessionPreferences.isLoggedIn()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun logout() {
        viewModelScope.launch {
            sessionPreferences.clearSession()
        }
    }
}