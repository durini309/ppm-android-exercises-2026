package dev.koalit.powersave.ejercicios.corrutinas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.koalit.powersave.ui.theme.PowerSaveTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun CorrutinasRoute(
    modifier: Modifier = Modifier,
    viewModel: CorrutinasViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CorrutinasScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
private fun CorrutinasScreen(
    state: CorrutinasState,
    onEvent: (CorrutinasEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        WithViewModelSection(
            state = state,
            onEvent = onEvent,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        HorizontalDivider()
        WithoutViewModelSection(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun WithViewModelSection(
    state: CorrutinasState,
    onEvent: (CorrutinasEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val isRunning = state.status == JobStatus.RUNNING
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Con ViewModel",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "${state.name}: ${state.age}",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = when (state.status) {
                JobStatus.IDLE -> "Job inactivo"
                JobStatus.RUNNING -> "Corriendo... tick ${state.tick}"
                JobStatus.CANCELLED -> "Cancelado. Último tick: ${state.tick}"
                JobStatus.FINISHED -> "Terminado en tick ${state.tick}"
            },
            style = MaterialTheme.typography.bodyLarge
        )
        // Selector de modo: bloqueado mientras el job está isRunning
        SingleChoiceSegmentedButtonRow {
            SegmentedButton(
                selected = !state.useThreadSleep,
                onClick = { onEvent(CorrutinasEvent.ModeChanged(useThreadSleep = false)) },
                enabled = !isRunning,
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
            ) {
                Text("delay")
            }
            SegmentedButton(
                selected = state.useThreadSleep,
                onClick = { onEvent(CorrutinasEvent.ModeChanged(useThreadSleep = true)) },
                enabled = !isRunning,
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
            ) {
                Text("Thread.sleep")
            }
        }
        Button(onClick = { onEvent(CorrutinasEvent.StartCancelResumeClicked) }) {
            // El progress va dentro del botón para que la pantalla no cambie de tamaño
            if (isRunning) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = LocalContentColor.current,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                when (state.status) {
                    JobStatus.IDLE, JobStatus.FINISHED -> "Iniciar"
                    JobStatus.RUNNING -> "Cancelar"
                    JobStatus.CANCELLED -> "Reanudar"
                }
            )
        }
    }
}

@Composable
private fun WithoutViewModelSection(
    modifier: Modifier = Modifier
) {
    // (a) LaunchedEffect: arranca solo al entrar a la composición
    // y se cancela automáticamente cuando el composable sale de pantalla.
    var secondsOnScreen by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1.seconds)
            secondsOnScreen++
        }
    }

    // (b) rememberCoroutineScope: solo lanza la corrutina cuando ocurre un evento (click)
    val scope = rememberCoroutineScope()
    var eventMessage by remember { mutableStateOf("Aún no has presionado el botón") }
    var timesPressed by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sin ViewModel",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "LaunchedEffect (automático): $secondsOnScreen s en pantalla",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "rememberCoroutineScope (por evento): $eventMessage",
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = {
                scope.launch {
                    timesPressed++
                    val number = timesPressed
                    eventMessage = "Corrutina #$number corriendo..."
                    delay(2.seconds)
                    eventMessage = "Corrutina #$number terminó"
                }
            }
        ) {
            Text("Lanzar corrutina")
        }
    }
}

@Preview
@Composable
private fun PreviewCorrutinasScreen() {
    PowerSaveTheme {
        Surface{
            CorrutinasScreen(
                state = CorrutinasState(
                    name = "Juan",
                    age = 4
                ),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
