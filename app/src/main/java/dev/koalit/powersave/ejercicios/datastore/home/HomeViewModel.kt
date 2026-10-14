package dev.koalit.powersave.ejercicios.datastore.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.koalit.powersave.ejercicios.datastore.DatastorePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val preferences = DatastorePreferences(application)

    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val email = preferences.getEmail()
            _state.update { email }
        }
    }
}