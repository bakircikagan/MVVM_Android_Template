package com.example.mvvm_android_template.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.application.coordinator.NavCommand
import com.example.mvvm_android_template.application.coordinator.Routable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var routes: Set<@JvmSuppressWildcards Routable>

    @Inject
    lateinit var coordinator: Coordinator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp(
                routes = routes,
                coordinator = coordinator
            )
        }
    }
}

// TO DO: Local provider dil icin ??
// Multiple activityden vazgecmek lazim
// in MainApp composable
@Composable
fun MainApp(
    routes: Set<Routable>,
    coordinator: Coordinator
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        coordinator.commands.collect { command ->
            when (command) {
                NavCommand.ToWelcome -> {
                    navController.navigate("welcome") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }

                NavCommand.ToProducts -> {
                    navController.navigate("products") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }

                is NavCommand.ToProductDetails -> {
                    navController.navigate("productDetails/${command.productId}")
                }

                NavCommand.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(
            bottomBar = {
                BottomTabs(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        ) { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "welcome"
                ) {
                    routes.forEach { it.register(this) }
                }
            }
        }
    }
}







