package dev.koalit.powersave.ejercicios.room.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.koalit.powersave.PowerSaveApp
import dev.koalit.powersave.ejercicios.room.data.Restaurante
import dev.koalit.powersave.ejercicios.room.data.RestaurantesDb
import dev.koalit.powersave.ejercicios.room.data.toEntity
import dev.koalit.powersave.ejercicios.room.data.toModelo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RestaurantesViewModel(
    application: Application
): AndroidViewModel(application) {


    private val _filtrarFavoritos = MutableStateFlow(0)
    val filtrarFavoritos = _filtrarFavoritos.asStateFlow()

    private val restDao = (application as PowerSaveApp).database.restauranteDao()

    // combine "escucha" dos flows al mismo tiempo: la lista que viene de Room y el filtro.
    // Cada vez que CUALQUIERA de los dos emite un valor nuevo, se vuelve a ejecutar la lambda
    // usando el último valor de cada uno. Así:
    //  - Si se inserta/borra/actualiza algo en la DB, Room emite la lista nueva -> se recalcula.
    //  - Si el usuario cambia el filtro, _filtrarFavoritos emite -> se recalcula con la misma lista.
    // Nota: combine no emite nada hasta que ambos flows hayan emitido al menos una vez.
    val rest = combine(restDao.getRestaurants(), _filtrarFavoritos) { restaurantes, filtro ->
        val filtrados = if (filtro == 1) {
            restaurantes.filter {
                it.esFavorito == 1
            }
        } else {
            restaurantes
        }

        // Convertimos de Entity (capa de datos) a Restaurante (modelo que usa la UI)
        filtrados.map { it.toModelo() }
    }.stateIn(
        // combine devuelve un Flow "frío"; stateIn lo convierte en un StateFlow que la UI
        // puede observar con collectAsStateWithLifecycle y que siempre tiene un valor actual.
        scope = viewModelScope,
        // Mantiene activa la consulta mientras haya alguien observando. Los 5 segundos extra
        // evitan reiniciar la consulta a Room en cambios de configuración (ej. rotar pantalla).
        started = SharingStarted.WhileSubscribed(5_000),
        // Valor que ve la UI mientras Room todavía no ha emitido la primera lista
        initialValue = listOf()
    )


    fun cambiarFiltro(mostrarFavoritos: Int) {
        _filtrarFavoritos.update {
            mostrarFavoritos
        }
    }

    fun syncDb() {
        val restaurantes = RestaurantesDb.restaurantes
        viewModelScope.launch {
            restDao.addRestaurants(restaurantes)
        }
    }

    fun deleteRestaurant(restaurante: Restaurante) {
        val entity = restaurante.toEntity()
        viewModelScope.launch {
            restDao.deleteRestaurant(entity)
        }
    }

    fun updateRestaurant(restaurante: Restaurante) {
        val nuevoEstado = !restaurante.esFavorito
        val entity = restaurante.copy(
            esFavorito = nuevoEstado
        ).toEntity()
        viewModelScope.launch {
            restDao.updateRestaurant(entity)
        }
    }
}