package dev.koalit.powersave.ejercicios.room.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.koalit.powersave.ejercicios.room.data.Restaurante
import dev.koalit.powersave.ejercicios.room.data.RestaurantesDb
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun RestaurantesScreen(modifier: Modifier = Modifier) {
    val restaurantes = RestaurantesDb.restaurantes
    LazyColumn(modifier) {
        items(restaurantes) {
            RestauranteItem(
                restaurante = it,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RestauranteItem(
    restaurante: Restaurante,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(restaurante.nombre)
            Text(restaurante.direccion)
        }
        TextButton(
            onClick = {}
        ) {
            Text("Borrar")
        }
    }
}

@Preview
@Composable
private fun PreviewRestauranteItem() {
    PowerSaveTheme{
        Surface() {
            RestaurantesScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}