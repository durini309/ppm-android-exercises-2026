package dev.koalit.powersave.labs

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.koalit.powersave.R
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun PortadaUvg(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 4.dp,
                color = Color.Green
            )
    ) {
        Image(
            painter = painterResource(R.drawable.runnio_logo),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
            alpha = 0.2f
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "Universidad del Valle de Guatemala",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Programación de plataformas móviles, Sección 30",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "INTEGRANTES",
                    fontWeight = FontWeight.Bold,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Juan Durini")
                    Text(text = "Ronaldo")
                    Text(text = "Messi")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "CATEDRATICO",
                    fontWeight = FontWeight.Bold,
                )
                Text(text = "Juan Durini")
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Juan Durini")
                Text(text = "12345")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPortadaUvg() {
    PowerSaveTheme {
        PortadaUvg(modifier = Modifier.fillMaxSize())
    }
}