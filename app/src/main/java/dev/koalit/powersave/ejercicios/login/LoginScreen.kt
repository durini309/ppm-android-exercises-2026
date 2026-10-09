package dev.koalit.powersave.ejercicios.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreen(
        state = state,
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        modifier = modifier
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.authenticated) {
            Text(
                text = "¡Bienvenido, ${state.email}!",
                style = MaterialTheme.typography.headlineSmall
            )
            return@Column
        }

        OutlinedTextField(
            value = state.email,
            onValueChange = { newValue ->
                onEvent(LoginEvent.OnEmailChange(newValue))
            },
            label = { Text("Correo") },
            enabled = !state.isLoading,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = { newValue ->
                onEvent(LoginEvent.OnPasswordChange(newValue))
            },
            label = { Text("Password") },
            enabled = !state.isLoading,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        if (state.hasError) {
            Text(
                text = "Error en credenciales",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Button(
            onClick = { onEvent(LoginEvent.LoginClick) },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Iniciar sesión")
            }
        }
        OutlinedButton(
            onClick = { onEvent(LoginEvent.CancelClick) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    PowerSaveTheme {
        Surface {
            LoginScreen(
                state = LoginState(),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreenError() {
    PowerSaveTheme {
        Surface {
            LoginScreen(
                state = LoginState(
                    email = "admin@uvg.edu.gt",
                    password = "0000",
                    hasError = true
                ),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreenLoading() {
    PowerSaveTheme {
        Surface {
            LoginScreen(
                state = LoginState(
                    email = "admin@uvg.edu.gt",
                    password = "1234",
                    isLoading = true
                ),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreenAuthenticated() {
    PowerSaveTheme {
        Surface {
            LoginScreen(
                state = LoginState(
                    email = "admin@uvg.edu.gt",
                    authenticated = true
                ),
                onEvent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
