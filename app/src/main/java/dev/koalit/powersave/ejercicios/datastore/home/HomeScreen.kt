package dev.koalit.powersave.ejercicios.datastore.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun HomeRoute(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
     viewModel: HomeViewModel = viewModel()
) {

     val state by viewModel.state.collectAsStateWithLifecycle()
    // val email
    HomeScreen(
        email = state,
        onLogoutClick = onLogoutClick,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    email: String,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Hola, $email!",
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedButton(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    PowerSaveTheme {
        Surface {
            HomeScreen(
                email = "admin@uvg.edu.gt",
                onLogoutClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
