package dev.koalit.powersave.ejercicios.nombres_random

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
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

@Composable
fun NombresRandomRoute(
    modifier: Modifier = Modifier,
    viewModel: NombresRandomViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    NombresRandomScreen(
        state = state,
        onStateChange = viewModel::changeState,
        modifier = modifier
    )
}

@Composable
private fun NombresRandomScreen(
    state: NombresRandomState,
    onStateChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.hasError -> {
                Text(
                    text = "Error: ${state.number}",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.error
                )
                FilledTonalButton(
                    onClick = onStateChange,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text("Reintentar")
                }
            }
            else -> {
                Text(
                    text = "${state.name}: ${state.number}",
                    style = MaterialTheme.typography.displayMedium,
                )
                FilledTonalButton(
                    onClick = onStateChange,
                ) {
                    Text("Cambiar")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNombresRandomScreen_Loading() {
    PowerSaveTheme {
        Surface {
            NombresRandomScreen(
                state = NombresRandomState(),
                onStateChange = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewNombresRandomScreen_Error() {
    PowerSaveTheme {
        Surface {
            NombresRandomScreen(
                state = NombresRandomState(
                    isLoading = false,
                    hasError = true,
                    number = "10"
                ),
                onStateChange = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewNombresRandomScreen_Success() {
    PowerSaveTheme {
        Surface {
            NombresRandomScreen(
                state = NombresRandomState(
                    isLoading = false,
                    hasError = false,
                    name = "Juan Carlos",
                    number = "6"
                ),
                onStateChange = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}