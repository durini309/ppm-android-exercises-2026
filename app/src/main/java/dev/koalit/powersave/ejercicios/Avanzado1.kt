package dev.koalit.powersave.ejercicios

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.koalit.powersave.ui.theme.PowerSaveTheme
import org.intellij.lang.annotations.JdkConstants

@Composable
fun OrdenModifiers(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 4.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(Color.Magenta)
                .padding(16.dp)
        ) {
            Text("Box 1")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 4.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
                .background(Color.Magenta)

        ) {
            Text("Box 2")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 4.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = Color.Magenta,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)

        ) {
            Text("Box 3")
        }
    }
}

@Composable
fun BanderaGuate(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Guatemala",
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .border(
                    width = 4.dp,
                    color = Color.Black
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = MaterialTheme.colorScheme.primary
                    )
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .background(
                            color = Color.Green.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .border(
                            width = 4.dp,
                            color = Color.Green,
                            shape = CircleShape
                        )
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = "G",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Green
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = MaterialTheme.colorScheme.primary
                    )
                    .border(
                        width = 4.dp,
                        color = Color.Blue
                    )
                    .alpha(0.1f)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewOrdenModifiers() {
    PowerSaveTheme {
        OrdenModifiers(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBanderaGuate() {
    PowerSaveTheme(
        darkTheme = false
    ) {
        Surface {
            BanderaGuate(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}