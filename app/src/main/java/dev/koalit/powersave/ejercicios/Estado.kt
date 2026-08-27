package dev.koalit.powersave.ejercicios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun Contador(
    modifier: Modifier = Modifier
) {
    var cont by rememberSaveable {
        mutableIntStateOf(0)
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contador: $cont",
            style = MaterialTheme.typography.displayMedium
        )
        OutlinedButton(
            onClick = {
                cont--
            }
        ) {
            Text("Incrementar")
        }
    }
}

@Composable
fun Formulario(
    modifier: Modifier = Modifier
) {
    var num1 by rememberSaveable {
        mutableStateOf("")
    }
    var num2 by rememberSaveable {
        mutableStateOf("")
    }
    var result by rememberSaveable {
        mutableIntStateOf(0)
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = {
                Text("Num 1")
            },
            value = num1,
            onValueChange = { num1 = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            label = {
                Text("Num 2")
            },
            value = num2,
            onValueChange = { num2 = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        FilledTonalButton(
            onClick = {
                result = num1.toInt() + num2.toInt()
            }
        ) {
            Text("Sumar")
        }
        Text(
            text = "Suma: $result",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


@Preview
@Composable
private fun PreviewContador() {
    PowerSaveTheme {
        Surface {
            Contador(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTextField() {
    PowerSaveTheme {
        Surface {
            Formulario(
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}