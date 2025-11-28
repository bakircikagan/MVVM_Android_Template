package com.example.mvvm_android_template.application.coordinator

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface Routable {
    val route: String
    fun register(builder: NavGraphBuilder)
}