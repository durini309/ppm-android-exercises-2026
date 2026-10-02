package dev.koalit.powersave.ejercicios.claseVM

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.koalit.powersave.ui.theme.PowerSaveTheme

data class PantallaVmState(
    val name: String = "Juan",
    val age: Int = 18
)

@Composable
fun PantallaVMRoute(
    modifier: Modifier = Modifier,
    viewModel: PantallaViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PantallaVMScreen(
        state = state,
        onCambiarClick = {
            viewModel.changeState()
        },
        modifier = modifier
    )
}

@Composable
private fun PantallaVMScreen(
    state: PantallaVmState,
    onCambiarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${state.name}: ${state.age}",
            style = MaterialTheme.typography.displayMedium
        )
        Button(
            onClick = onCambiarClick
        ) {
            Text("Cambiar nombres")
        }
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun PreviewPantallaVMScreen() {
    PowerSaveTheme {
        Surface{
            PantallaVMScreen(
                state = PantallaVmState(
                    name = "Juan",
                    age = 4
                ),
                onCambiarClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

