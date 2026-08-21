package dev.koalit.powersave.ejercicios

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers.BLUE_DOMINATED_EXAMPLE
import androidx.compose.ui.tooling.preview.Wallpapers.GREEN_DOMINATED_EXAMPLE
import androidx.compose.ui.tooling.preview.Wallpapers.RED_DOMINATED_EXAMPLE
import androidx.compose.ui.unit.dp
import dev.koalit.powersave.R
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun FormularioLogin(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_apartment),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            trailingIcon = {
                Text("O")
            },
            placeholder = {
                Text("Ingresa tu nombre")
            },
            label = {
                Text("Nombre completo")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text("Ingresa tu contraseña")
            },
            label = {
                Text("Password")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(

        ) {
            OutlinedButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Crear",
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier.weight(1f)

            ) {
                Text("Crear")
            }
            FilledTonalButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text("Iniciar")
            }
            Icon(
                painter = painterResource(R.drawable.ic_apartment),
                contentDescription = null
            )
            FilledTonalIconButton(
                onClick = {},
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_apartment),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(
    wallpaper = GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun PreviewFormularioLogin() {
    PowerSaveTheme(
        darkTheme = false
    ) {
        Surface {
            FormularioLogin(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(64.dp)
            )
        }
    }
}
@Preview(
    wallpaper = BLUE_DOMINATED_EXAMPLE
)
@Composable
private fun PreviewFormularioLoginDark() {
    PowerSaveTheme(
        darkTheme = false
    ) {
        Surface {
            FormularioLogin(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(64.dp)
            )
        }
    }
}