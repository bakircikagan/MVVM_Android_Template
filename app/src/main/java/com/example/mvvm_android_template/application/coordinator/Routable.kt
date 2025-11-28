package com.example.mvvm_android_template.application.coordinator

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface Routable {
    // silinicek routeConfig kullanilicak
    val route: String

    val routeConfig: RouteConfig
    fun register(builder: NavGraphBuilder)
}