package dev.koalit.powersave.ejercicios.room.data

data class Restaurante(
    val id: Int,
    val nombre: String,
    val tipoCocina: String,
    val direccion: String,
    val calificacion: Double,
    val esFavorito: Boolean = false,
)

fun Restaurante.toEntity(): RestauranteEntity {
    return RestauranteEntity(
        id = id,
        nombre = nombre,
        tipoCocina = tipoCocina,
        direccion = direccion,
        calificacion = calificacion,
        esFavorito = if (esFavorito) 1 else 0
    )
}