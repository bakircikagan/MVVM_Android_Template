package com.example.mvvm_android_template.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.mvvm_android_template.application.coordinator.ActiveApp
import com.example.mvvm_android_template.application.coordinator.Routable
import com.example.mvvm_android_template.application.coordinator.Route
import com.example.mvvm_android_template.application.coordinator.RouteDiscovery

@Composable
fun BrochureApp(
    routes: Set<Routable>,
    routeDiscovery: RouteDiscovery,
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    // Start destination for BrochureApp
    val startDestinationPath = remember {
        routeDiscovery.getStartDestination(ActiveApp.BROCHURES)?.path
            ?: Route.Brochures.path   // sensible fallback
    }

    NavHost(
        navController = navController,
        startDestination = startDestinationPath,
        modifier = Modifier.padding(innerPadding)
    ) {
        routes
            .filter { it.routeConfig.app == ActiveApp.BROCHURES }
            .forEach { it.register(this) }
    }
}
