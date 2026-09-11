package dev.koalit.powersave.labs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.koalit.powersave.ui.theme.PowerSaveTheme

private val VerdeIncremento = Color(0xFF0F7B0F)
private val RojoDecremento = Color(0xFFB03A2E)

/**
 * Todo el estado vive en esta pantalla y se pasa hacia abajo (state hoisting):
 * los composables hijos solo reciben valores y lambdas.
 */
@Composable
fun PantallaContador(
    modifier: Modifier = Modifier
) {
    // Estado principal: el valor del contador.
    var contador by rememberSaveable { mutableIntStateOf(0) }

    // Estadísticas derivadas de la interacción del usuario.
    var incrementos by rememberSaveable { mutableIntStateOf(0) }
    var decrementos by rememberSaveable { mutableIntStateOf(0) }
    var valorMaximo by rememberSaveable { mutableIntStateOf(0) }
    var valorMinimo by rememberSaveable { mutableIntStateOf(0) }

    // Historial de valores por los que ha pasado el contador.
    var historial by rememberSaveable { mutableStateOf(emptyList<Int>()) }

    Column(modifier = modifier) {
        // El contenido crece con el historial, así que va dentro de una columna
        // con scroll que ocupa el espacio disponible (weight) y deja el botón fijo abajo.
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Juan Carlos Durini",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center
            )

            ControlesContador(
                contador = contador,
                onIncrementar = {
                    contador += 1
                    incrementos += 1
                    valorMaximo = maxOf(valorMaximo, contador)
                    valorMinimo = minOf(valorMinimo, contador)
                    historial = historial + contador
                },
                onDecrementar = {
                    contador -= 1
                    decrementos += 1
                    valorMaximo = maxOf(valorMaximo, contador)
                    valorMinimo = minOf(valorMinimo, contador)
                    historial = historial + contador
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            HorizontalDivider()

            Estadisticas(
                incrementos = incrementos,
                decrementos = decrementos,
                valorMaximo = valorMaximo,
                valorMinimo = valorMinimo,
                historial = historial,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        Button(
            onClick = {
                contador = 0
                incrementos = 0
                decrementos = 0
                valorMaximo = 0
                valorMinimo = 0
                historial = emptyList()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Reiniciar")
        }
    }
}

@Composable
private fun ControlesContador(
    contador: Int,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        FilledIconButton(
            onClick = onDecrementar,
            modifier = Modifier.size(40.dp)
        ) {
            Text(text = "−", fontSize = 22.sp)
        }
        Text(
            text = contador.toString(),
            fontSize = 56.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        FilledIconButton(
            onClick = onIncrementar,
            modifier = Modifier.size(40.dp)
        ) {
            Text(text = "+", fontSize = 22.sp)
        }
    }
}

@Composable
private fun Estadisticas(
    incrementos: Int,
    decrementos: Int,
    valorMaximo: Int,
    valorMinimo: Int,
    historial: List<Int>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        FilaEstadistica(etiqueta = "Total incrementos:", valor = incrementos)
        FilaEstadistica(etiqueta = "Total decrementos:", valor = decrementos)
        FilaEstadistica(etiqueta = "Valor máximo:", valor = valorMaximo)
        FilaEstadistica(etiqueta = "Valor mínimo:", valor = valorMinimo)
        FilaEstadistica(etiqueta = "Total cambios:", valor = incrementos + decrementos)

        Text(
            text = "Historial:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        HistorialContador(historial = historial)
    }
}

@Composable
private fun FilaEstadistica(
    etiqueta: String,
    valor: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = valor.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HistorialContador(
    historial: List<Int>,
    modifier: Modifier = Modifier
) {
    // FlowRow acomoda las fichas de izquierda a derecha y salta de fila al llenarse.
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        historial.forEachIndexed { indice, valor ->
            val anterior = if (indice == 0) 0 else historial[indice - 1]
            FichaHistorial(
                valor = valor,
                fueIncremento = valor > anterior
            )
        }
    }
}

@Composable
private fun FichaHistorial(
    valor: Int,
    fueIncremento: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (fueIncremento) VerdeIncremento else RojoDecremento,
        contentColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.width(36.dp)
    ) {
        Text(
            text = valor.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPantallaContadorLight() {
    PowerSaveTheme(darkTheme = false) {
        Surface {
            PantallaContador(modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPantallaContadorDark() {
    PowerSaveTheme(darkTheme = true) {
        Surface {
            PantallaContador(modifier = Modifier.fillMaxSize())
        }
    }
}
