package dev.koalit.powersave.ejercicios.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RestauranteEntity(
    @PrimaryKey
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

fun RestauranteEntity.mapToModel(): Restaurante {
    return Restaurante(
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
