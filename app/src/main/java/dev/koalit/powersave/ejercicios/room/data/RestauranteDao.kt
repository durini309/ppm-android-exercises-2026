package dev.koalit.powersave.ejercicios.room.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RestauranteDao {
    // IGNORE: si ya existe un registro con el mismo id, lo deja como está en lugar de lanzar
    // una excepción. Sin esto, la app se cae al presionar "Sincronizar" por segunda vez.
    // (REPLACE también evitaría el crash, pero sobreescribiría la fila y se perderían los favoritos)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRestaurants(restaurantes: List<RestauranteEntity>)

    @Query("SELECT * FROM restauranteentity")
    fun getRestaurants(): Flow<List<RestauranteEntity>>

    @Delete
    suspend fun deleteRestaurant(restauranteEntity: RestauranteEntity)

    @Update
    suspend fun updateRestaurant(restauranteEntity: RestauranteEntity)
}