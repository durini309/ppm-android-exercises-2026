package dev.koalit.powersave.ejercicios.room.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.koalit.powersave.ejercicios.room.Dependencias
import dev.koalit.powersave.ejercicios.room.data.Restaurante
import dev.koalit.powersave.ejercicios.room.data.RestaurantesDb
import dev.koalit.powersave.ejercicios.room.data.mapToEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RestaurantesViewModel(
    application: Application
): AndroidViewModel(application) {

    private val _rest: MutableStateFlow<List<Restaurante>> = MutableStateFlow(listOf())
    val rest = _rest.asStateFlow()

    init {
        Dependencias.buildDatabase(application)
        val restDao = Dependencias.database.restauranteDao()
        val restaurantes = RestaurantesDb.restaurantes
        viewModelScope.launch {
            restDao.addRestaurants(
                restaurantes.map {
                    it.mapToEntity()
                }
            )
        }
    }
}