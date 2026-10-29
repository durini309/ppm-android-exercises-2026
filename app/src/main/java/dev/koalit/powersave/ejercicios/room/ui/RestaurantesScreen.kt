package dev.koalit.powersave.ejercicios.room.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.koalit.powersave.R
import dev.koalit.powersave.ejercicios.room.data.Restaurante
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun RestaurantesRoute(
    modifier: Modifier = Modifier,
    viewModel: RestaurantesViewModel = viewModel()
) {
    val restaurantes by viewModel.rest.collectAsStateWithLifecycle()
    val filtrarFavoritos by viewModel.filtrarFavoritos.collectAsStateWithLifecycle()
    RestaurantesScreen(
        restaurantes = restaurantes,
        filtrarFavoritos = filtrarFavoritos,
        onFiltrarClick = viewModel::cambiarFiltro,
        onFavoritoClick = viewModel::updateRestaurant,
        onBorrarClick = viewModel::deleteRestaurant,
        syncDb = viewModel::syncDb,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantesScreen(
    restaurantes: List<Restaurante>,
    syncDb: () -> Unit,
    filtrarFavoritos: Int,
    onFiltrarClick: (Int) -> Unit,
    onFavoritoClick: (Restaurante) -> Unit,
    onBorrarClick: (Restaurante) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Room") },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = {
                                val nuevoValor = if (filtrarFavoritos == 1) {
                                    0
                                } else {
                                    1
                                }
                                onFiltrarClick(nuevoValor)
                            }
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (filtrarFavoritos == 1) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                                ),
                                contentDescription = "Favorito"
                            )
                        }
                        IconButton(
                            onClick = syncDb
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_sync),
                                contentDescription = "Sincronizar"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(restaurantes) {
                RestauranteItem(
                    onFavoritoClick = onFavoritoClick,
                    onBorrarClick = onBorrarClick,
                    restaurante = it,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun RestauranteItem(
    restaurante: Restaurante,
    onFavoritoClick: (Restaurante) -> Unit,
    onBorrarClick: (Restaurante) -> Unit,
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onFavoritoClick(restaurante)
                }
            ) {
                Icon(
                    painter = painterResource(
                        if (restaurante.esFavorito) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                    ),
                    contentDescription = "Favorito"
                )
            }
            TextButton(
                onClick = {
                    onBorrarClick(restaurante)
                }
            ) {
                Text("Borrar")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRestauranteItem() {
    PowerSaveTheme{
        Surface() {
            RestaurantesScreen(
                syncDb = {},
                filtrarFavoritos = 0,
                onFiltrarClick = {},
                onFavoritoClick = {},
                onBorrarClick = {},
                restaurantes = listOf(
                    Restaurante(
                        id = 1,
                        nombre = "Ookii",
                        direccion = "Zona 10",
                        tipoCocina = "Poke",
                        calificacion = 5.0,
                        esFavorito = false
                    )
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}