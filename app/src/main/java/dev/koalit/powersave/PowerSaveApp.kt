package dev.koalit.powersave

import android.app.Application
import androidx.room3.Room
import dev.koalit.powersave.ejercicios.room.data.AppDatabase

class PowerSaveApp : Application() {

    // Solo existe una Application por app, así que la base de datos también es una sola.
    // "by lazy" la crea la primera vez que alguien la usa (y ya es thread-safe).
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "uvg.db"
        ).build()
    }
}
