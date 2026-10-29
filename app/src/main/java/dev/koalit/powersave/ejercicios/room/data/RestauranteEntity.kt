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
    val esFavorito: Int = 0,
)

fun RestauranteEntity.toModelo(): Restaurante {
    return Restaurante(
        id = id,
        nombre = nombre,
        tipoCocina = tipoCocina,
        direccion = direccion,
        calificacion = calificacion,
        esFavorito = if (esFavorito == 1) true else false
    )
}