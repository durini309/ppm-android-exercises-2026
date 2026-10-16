package dev.koalit.powersave.ejercicios.room

import android.content.Context
import androidx.room.Room
import dev.koalit.powersave.ejercicios.room.data.AppDatabase

object Dependencias {
    lateinit var database: AppDatabase

    fun buildDatabase(context: Context) {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "uvg.db"
        ).build()
    }

}

