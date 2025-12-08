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
fun CommerceApp(
    routes: Set<Routable>,
    routeDiscovery: RouteDiscovery,
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    // Start destination for CommerceApp
    val startDestinationPath = remember {
        routeDiscovery.getStartDestination(ActiveApp.E_COMMERCE)?.path
            ?: Route.Products.path   // sensible fallback
    }

    NavHost(
        navController = navController,
        startDestination = startDestinationPath,
        modifier = Modifier.padding(innerPadding)
    ) {
        routes
            .filter { it.routeConfig.app == ActiveApp.E_COMMERCE }
            .forEach { it.register(this) }
    }
}
