package dev.koalit.powersave.ejercicios.room.data

data class Restaurante(
    val id: Int,
    val nombre: String,
    val tipoCocina: String,
    val direccion: String,
    val ciudad: String,
    val telefono: String,
    val calificacion: Double,
    val precioPromedio: Double,
    val capacidad: Int,
    val aceptaReservas: Boolean,
    val tieneParqueo: Boolean,
)

fun Restaurante.mapToEntity(): RestauranteEntity {
    return RestauranteEntity(
        id = id,
        nombre = nombre,
        tipoCocina = tipoCocina,
        direccion = direccion,
        ciudad = ciudad,
        telefono = telefono,
        calificacion = calificacion,
        precioPromedio = precioPromedio,
        capacidad = capacidad,
        aceptaReservas = aceptaReservas,
        tieneParqueo = tieneParqueo
    )
}