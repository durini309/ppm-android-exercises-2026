package dev.koalit.powersave.labs

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers.BLUE_DOMINATED_EXAMPLE
import androidx.compose.ui.tooling.preview.Wallpapers.RED_DOMINATED_EXAMPLE
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import dev.koalit.powersave.R
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun PantallaJornada(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        BannerActualizacion(
            onDescargar = {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        "https://play.google.com/store/apps/details?id=com.whatsapp".toUri()
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        EncabezadoJornada(
            onTerminarJornada = {
                Toast.makeText(context, "Jornada terminada", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )
        TarjetaRestaurante(
            onIniciar = {
                Toast.makeText(context, "Juan Carlos Durini", Toast.LENGTH_SHORT).show()
            },
            onDetalles = {
                Toast.makeText(context, "Comida japonesa\nQQQ", Toast.LENGTH_LONG).show()
            },
            onDirections = {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        "geo:14.5988,-90.5127?q=14.5988,-90.5127(Ookii)".toUri()
                    )
                )
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun BannerActualizacion(
    onDescargar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_inclusive),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                )
            }
            Text(
                text = "Actualización disponible",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onDescargar) {
                Text(text = "Descargar")
            }
        }
    }
}

@Composable
private fun EncabezadoJornada(
    onTerminarJornada: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Miércoles",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "30 de septiembre",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        OutlinedButton(
            onClick = onTerminarJornada,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Terminar jornada")
        }
    }
}

@Composable
private fun TarjetaRestaurante(
    onIniciar: () -> Unit,
    onDetalles: () -> Unit,
    onDirections: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Ookii",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDirections) {
                    Icon(
                        painter = painterResource(R.drawable.ic_apartment),
                        contentDescription = "Direcciones",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                text = "Plaza 10, 6a avenida zona 10",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "8:00AM - 7:00PM",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Button(
                    onClick = onIniciar,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Iniciar")
                }
                TextButton(
                    onClick = onDetalles,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Detalles")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPantallaJornadaLight() {
    PowerSaveTheme(darkTheme = false) {
        Surface {
            PantallaJornada(modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPantallaJornadaDark() {
    PowerSaveTheme(darkTheme = true) {
        Surface {
            PantallaJornada(modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true, wallpaper = RED_DOMINATED_EXAMPLE)
@Composable
private fun PreviewPantallaJornadaDinamico() {
    PowerSaveTheme(darkTheme = false) {
        Surface {
            PantallaJornada(modifier = Modifier.fillMaxSize())
        }
    }
}
