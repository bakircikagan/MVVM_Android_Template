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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvvm_android_template.application.coordinator.ActiveApp
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.application.coordinator.Routable
import com.example.mvvm_android_template.application.coordinator.Route
import com.example.mvvm_android_template.application.coordinator.RouteDiscovery
import com.example.mvvm_android_template.application.view_model.AppSwitcherViewModel
import com.example.mvvm_android_template.presentation.view.AppSwitcherTopBar
import com.example.mvvm_android_template.presentation.view.UniversalBottomBar
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
//            MainApp(
//                routes = routes,
//                coordinator = coordinator,
//                routeDiscovery = routeDiscovery
//            )
            RootApp(
                routes = routes,
                coordinator = coordinator,
                routeDiscovery = routeDiscovery
            )

        }
    }
}

@Composable
fun MainApp(
    routes: Set<Routable>,
    coordinator: Coordinator,
    routeDiscovery: RouteDiscovery
) {
    val navController = rememberNavController()

    // App switcher VM
    val appSwitcherViewModel: AppSwitcherViewModel = hiltViewModel()
    val activeApp by appSwitcherViewModel.activeApp.observeAsState()

    // Step 1: choose a default app explicitly
    val defaultApp = ActiveApp.E_COMMERCE

    // Step 2: compute start destination for default app
    val initialStartDestination: Route =
        routeDiscovery.getStartDestination(defaultApp) ?: Route.Products

    LaunchedEffect(Unit) {
        coordinator.attachNavController(navController)
        coordinator.start()
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // If VM hasn’t emitted yet, use default app
    val currentActiveApp = activeApp ?: defaultApp

    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(
            topBar = {
                AppSwitcherTopBar(
                    activeApp = currentActiveApp,
                    onAppSelected = { app ->
                        appSwitcherViewModel.switchApp(app)

                        // Step 3: when user switches app, navigate to that app's start route
                        val startDestination = routeDiscovery.getStartDestination(app)
                        if (startDestination != null) {
                            navController.navigate(startDestination.path) {
                                // clear the whole stack when changing apps
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            },
            bottomBar = {
                UniversalBottomBar(
                    currentRoute = currentRoute,
                    app = currentActiveApp
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
                    startDestination = initialStartDestination.path
                ) {
                    routes.forEach { it.register(this) }
                }
            }
        }
    }
}


