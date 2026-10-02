package dev.koalit.powersave.ejercicios.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun TimerRoute(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TimerScreen(
        state = state,
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        modifier = modifier
    )
}

@Composable
private fun TimerScreen(
    state: TimerScreenState,
    onEvent: (TimerScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.timeStr,
            onValueChange = { newValue ->
                onEvent(TimerScreenEvent.OnTimeChange(newValue))
            },
            label = { Text("Tiempo") },
            // Solo se puede editar antes de iniciar
            enabled = state.timerStatus == TimerStatus.IDLE,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TimerButtons(
            status = state.timerStatus,
            canStart = (state.timeStr.toIntOrNull() ?: 0) > 0,
            onEvent = onEvent
        )
        Text(
            text = state.counter.toString(),
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Composable
private fun TimerButtons(
    status: TimerStatus,
    canStart: Boolean,
    onEvent: (TimerScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (status) {
            TimerStatus.IDLE -> {
                Button(
                    onClick = { onEvent(TimerScreenEvent.StartClick) },
                    enabled = canStart,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Iniciar")
                }
            }
            TimerStatus.RUNNING -> {
                Button(
                    onClick = { onEvent(TimerScreenEvent.PauseClick) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Pausar")
                }
                RestartButton(onEvent = onEvent, modifier = Modifier.weight(1f))
            }
            TimerStatus.PAUSED -> {
                Button(
                    onClick = { onEvent(TimerScreenEvent.ResumeClick) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reanudar")
                }
                RestartButton(onEvent = onEvent, modifier = Modifier.weight(1f))
            }
            TimerStatus.FINISHED -> {
                RestartButton(onEvent = onEvent, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun RestartButton(
    onEvent: (TimerScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { onEvent(TimerScreenEvent.RestartClick) },
        modifier = modifier
    ) {
        Text("Reiniciar")
    }
}

@Preview
@Composable
private fun PreviewTimerScreenIdle() {
    PowerSaveTheme {
        Surface {
            TimerScreen(
                state = TimerScreenState(),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTimerScreenRunning() {
    PowerSaveTheme {
        Surface {
            TimerScreen(
                state = TimerScreenState(
                    counter = 5,
                    timeStr = "8",
                    timerStatus = TimerStatus.RUNNING
                ),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTimerScreenPaused() {
    PowerSaveTheme {
        Surface {
            TimerScreen(
                state = TimerScreenState(
                    counter = 5,
                    timeStr = "8",
                    timerStatus = TimerStatus.PAUSED
                ),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTimerScreenFinished() {
    PowerSaveTheme {
        Surface {
            TimerScreen(
                state = TimerScreenState(
                    counter = 8,
                    timeStr = "8",
                    timerStatus = TimerStatus.FINISHED
                ),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
