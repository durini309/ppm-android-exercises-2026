package dev.koalit.powersave.ejercicios.room.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.koalit.powersave.PowerSaveApp
import dev.koalit.powersave.ejercicios.room.data.RestauranteEntity
import dev.koalit.powersave.ejercicios.room.data.RestaurantesDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RestaurantesViewModel(
    application: Application
): AndroidViewModel(application) {

    private val _rest: MutableStateFlow<List<RestauranteEntity>> = MutableStateFlow(listOf())
    val rest = _rest.asStateFlow()

    private val restDao = (application as PowerSaveApp).database.restauranteDao()
    init {

        val restaurantes = RestaurantesDb.restaurantes
        viewModelScope.launch {
            restDao.addRestaurants(restaurantes)
        }
    }
}