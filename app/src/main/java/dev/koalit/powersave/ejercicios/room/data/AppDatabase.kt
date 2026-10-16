package dev.koalit.powersave.ejercicios.room.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        RestauranteEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restauranteDao() : RestauranteDao
}
