package dev.koalit.powersave.ejercicios.datastore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.koalit.powersave.ejercicios.datastore.home.HomeRoute
import dev.koalit.powersave.ejercicios.datastore.home.HomeScreen
import dev.koalit.powersave.ejercicios.datastore.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object SplashDestination

@Serializable
data object LoginDestination

@Serializable
data object HomeDestination

@Composable
fun SessionNavHost(
    modifier: Modifier = Modifier,
    viewModel: SessionViewModel = viewModel()
) {
    val navController = rememberNavController()
    val isLogged by viewModel.isLogged.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = SplashDestination,
        modifier = modifier
    ) {
        composable<SplashDestination> {
            SplashScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
        composable<LoginDestination> {
            LoginRoute(
                modifier = Modifier.fillMaxSize()
            )
        }
        composable<HomeDestination> {
            HomeRoute(
                onLogoutClick = { viewModel.logout() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    // Cada vez que isLogged cambia su valor, ejecutamos el código adentro
    LaunchedEffect(isLogged) {
        when (isLogged) {
            null -> { }
            true -> {
                navController.navigate(HomeDestination)
            }
            else -> {
                navController.navigate(LoginDestination)
            }
        }
    }
}
