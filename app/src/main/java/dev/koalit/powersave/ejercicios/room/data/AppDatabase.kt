package dev.koalit.powersave.ejercicios.room.data

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [
        RestauranteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restauranteDao() : RestauranteDao
}
