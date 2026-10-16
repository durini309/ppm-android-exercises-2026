package dev.koalit.powersave.ejercicios.room.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RestauranteDao {
    @Insert
    suspend fun addRestaurants(restaurantes: List<RestauranteEntity>)

    @Query("SELECT * FROM restauranteentity")
    fun getRestaurants(): Flow<List<RestauranteEntity>>

    // TODO: Eliminar
}