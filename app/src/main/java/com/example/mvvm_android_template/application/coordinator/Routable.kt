package com.example.mvvm_android_template.application.coordinator

import androidx.navigation.NavGraphBuilder

interface Routable {
    val routeConfig: RouteConfig

    fun register(builder: NavGraphBuilder)
}