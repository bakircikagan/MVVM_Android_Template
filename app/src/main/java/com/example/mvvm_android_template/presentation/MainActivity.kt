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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.application.coordinator.NavCommand
import com.example.mvvm_android_template.application.coordinator.Routable
import com.example.mvvm_android_template.application.coordinator.RouteDiscovery
import com.example.mvvm_android_template.application.view_model.ActiveApp
import com.example.mvvm_android_template.application.view_model.AppSwitcherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var routes: Set<@JvmSuppressWildcards Routable>

    @Inject
    lateinit var coordinator: Coordinator

    @Inject
    lateinit var routeDiscovery: RouteDiscovery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp(
                routes = routes,
                coordinator = coordinator,
                routeDiscovery = routeDiscovery
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
    coordinator: Coordinator,
    routeDiscovery: RouteDiscovery
) {
    val navController = rememberNavController()

    // 🔹 App switcher VM
    val appSwitcherViewModel: AppSwitcherViewModel = hiltViewModel()
    val activeApp by appSwitcherViewModel.activeApp.observeAsState()

    // Get start destination from RouteDiscovery for the first discovered app
    val firstApp = routeDiscovery.getActivities().firstOrNull()
    val initialStartDestination = firstApp?.let { routeDiscovery.getStartDestination(it) } ?: "welcome"

    // 🔹 Listen to Coordinator commands (your existing logic + brochures if you added)
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

                // If you defined these:
                NavCommand.ToBrochures -> {
                    navController.navigate("brochures") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }

                NavCommand.ToCategories -> {
                    navController.navigate("categories") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }

                is NavCommand.ToBrochureDetails -> {
                    navController.navigate("brochureDetails/${command.brochureId}")
                }

                NavCommand.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Don't render until we have an active app
    val currentActiveApp = activeApp ?: return

    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(
            topBar = {
                AppSwitcherTopBar(
                    activeApp = currentActiveApp,
                    onAppSelected = { app ->
                        appSwitcherViewModel.switchApp(app)

                        // 🔹 Automatically navigate to the app's start destination
                        val startDestination = routeDiscovery.getStartDestination(app)
                        if (startDestination != null) {
                            navController.navigate(startDestination) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            },
            bottomBar = {
                // Universal bottom bar - automatically displays tabs for current activity
                UniversalBottomBar(
                    navController = navController,
                    currentRoute = currentRoute,
                    activity = currentActiveApp
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = initialStartDestination
                ) {
                    routes.forEach { it.register(this) }
                }
            }
        }
    }
}

