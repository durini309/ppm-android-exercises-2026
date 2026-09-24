package dev.koalit.powersave.ejercicios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.koalit.powersave.R
import dev.koalit.powersave.ui.theme.PowerSaveTheme
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object DashboardRoute

@Serializable
data object ProfileRoute

@Composable
fun AppNavigation2(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
        modifier = modifier.fillMaxSize()
    ) {
        composable<LoginRoute> {
            PantallaLogin(
                // (1) CON back stack: navigate normal, Login se queda abajo.
                // Back en Dashboard -> regresa a Login.
                onLoginConBackStack = {
                    navController.navigate(DashboardRoute)
                },
                // (2) SIN back stack: popUpTo<LoginRoute> con inclusive = true
                // borra Login antes de entrar a Dashboard.
                // Back en Dashboard -> cierra la app.
                onLoginSinBackStack = {
                    navController.navigate(DashboardRoute) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<DashboardRoute> {
            PantallaDashboard(
                onIrAProfile = { navController.navigate(ProfileRoute) },
                // popBackStack() quita la pantalla actual del back stack.
                // Si ya no hay nada abajo devuelve false y no pasa nada:
                // para ver que la app se cierra hay que usar el back del sistema.
                onBack = { navController.popBackStack() }
            )
        }

        composable<ProfileRoute> {
            PantallaProfile(
                // (1) CON back stack: apilamos Login encima de Profile.
                // Back en Login -> regresa a Profile (bug clasico de un logout mal hecho).
                onLogoutConBackStack = {
                    navController.navigate(LoginRoute)
                },
                // (2) SIN back stack: limpiamos todo hasta el destino inicial
                // del grafo (inclusive) y dejamos Login como unica pantalla.
                // Back en Login -> cierra la app.
                onLogoutSinBackStack = {
                    navController.navigate(LoginRoute) {
                        popUpTo(0) { }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PantallaLogin(
    onLoginConBackStack: () -> Unit,
    onLoginSinBackStack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.displayLarge
        )
        OutlinedButton(onClick = onLoginConBackStack) {
            Text("Login Con BackStack (1)")
        }
        OutlinedButton(onClick = onLoginSinBackStack) {
            Text("Login Sin BackStack (2)")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PantallaDashboard(
    onIrAProfile: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.displayLarge
            )
            OutlinedButton(onClick = onIrAProfile) {
                Text("Profile")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PantallaProfile(
    onLogoutConBackStack: () -> Unit,
    onLogoutSinBackStack: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.displayLarge
            )
            OutlinedButton(onClick = onLogoutConBackStack) {
                Text("Logout (1)")
            }
            OutlinedButton(onClick = onLogoutSinBackStack) {
                Text("Logout (2)")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPantallaLogin() {
    PowerSaveTheme {
        Surface {
            PantallaLogin(
                onLoginConBackStack = {},
                onLoginSinBackStack = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPantallaDashboard() {
    PowerSaveTheme {
        Surface {
            PantallaDashboard(
                onIrAProfile = {},
                onBack = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPantallaProfile() {
    PowerSaveTheme {
        Surface {
            PantallaProfile(
                onLogoutConBackStack = {},
                onLogoutSinBackStack = {},
                onBack = {}
            )
        }
    }
}
