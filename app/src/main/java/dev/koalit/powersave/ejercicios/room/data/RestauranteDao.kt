package dev.koalit.powersave.ejercicios.room.data

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RestauranteDao {
    @Insert
    suspend fun addRestaurants(restaurantes: List<RestauranteEntity>)

    @Query("SELECT * FROM restauranteentity")
    fun getRestaurants(): Flow<List<RestauranteEntity>>

    // TODO: Eliminar
}