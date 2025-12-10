package com.example.mvvm_android_template.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvvm_android_template.application.coordinator.ActiveApp
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.application.coordinator.Routable
import com.example.mvvm_android_template.application.coordinator.RouteDiscovery
import com.example.mvvm_android_template.application.view_model.AppSwitcherViewModel
import com.example.mvvm_android_template.presentation.view.AppSwitcherTopBar
import com.example.mvvm_android_template.presentation.view.UniversalBottomBar

@Composable
fun RootApp(
    routes: Set<Routable>,
    coordinator: Coordinator,
    routeDiscovery: RouteDiscovery
) {
    val navController = rememberNavController()

    val appSwitcherViewModel: AppSwitcherViewModel = hiltViewModel()
    val activeApp by appSwitcherViewModel.activeApp.observeAsState(ActiveApp.E_COMMERCE)

    LaunchedEffect(Unit) {
        coordinator.attachNavController(navController)
        coordinator.start()
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(
            topBar = {
                AppSwitcherTopBar(
                    activeApp = activeApp,
                    onAppSelected = { app ->
                        appSwitcherViewModel.switchApp(app)
                    }
                )
            },
            bottomBar = {
                // ✅ Use your existing UniversalBottomBar
                UniversalBottomBar(
                    currentRoute = currentRoute,
                    app = activeApp
                )
            }
        ) { innerPadding ->
            when (activeApp) {
                ActiveApp.E_COMMERCE -> {
                    CommerceApp(
                        routes = routes,
                        routeDiscovery = routeDiscovery,
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }

                ActiveApp.BROCHURES -> {
                    BrochureApp(
                        routes = routes,
                        routeDiscovery = routeDiscovery,
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}
