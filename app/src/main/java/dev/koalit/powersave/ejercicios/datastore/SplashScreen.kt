package dev.koalit.powersave.ejercicios.datastore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PowerSave",
            style = MaterialTheme.typography.headlineMedium
        )
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun PreviewSplashScreen() {
    PowerSaveTheme {
        Surface {
            SplashScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
