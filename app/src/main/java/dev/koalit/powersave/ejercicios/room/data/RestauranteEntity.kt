package dev.koalit.powersave.ejercicios.room.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class RestauranteEntity(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val tipoCocina: String,
    val direccion: String,
    val calificacion: Double,
    val esFavorito: Boolean = false,
)
