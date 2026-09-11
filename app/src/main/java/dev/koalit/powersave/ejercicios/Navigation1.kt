package dev.koalit.powersave.ejercicios

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data object HomeDestination

@Serializable
data object UserListDestination

@Serializable
data class ProfileDestination(
    val name: String
)

val users = listOf(
    "Juan", "Carlos", "Maria", "Elena", "Pedro"
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        startDestination = HomeDestination,
        navController = navController,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<HomeDestination> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.displayLarge
                )
                Button(
                    onClick = {
                        navController.navigate(
                            route = UserListDestination
                        )
                    }
                ) {
                    Text("Navegar a listado")
                }
            }
        }
        composable<UserListDestination> {
            val context = LocalContext.current
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Usuarios",
                    style = MaterialTheme.typography.displayLarge
                )
                Column() {
                    users.forEach {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.clickable(
                                enabled = true
                            ) {
                                navController.navigate(
                                    route = ProfileDestination(
                                        name = it
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

        composable<ProfileDestination> { backStackEntry ->
            val currentDestination: ProfileDestination = backStackEntry.toRoute()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = currentDestination.name,
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }
}