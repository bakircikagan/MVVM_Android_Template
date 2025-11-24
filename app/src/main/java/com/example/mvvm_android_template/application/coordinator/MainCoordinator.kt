package com.example.mvvm_android_template.presentation.coordinator

import androidx.navigation.NavHostController

class MainCoordinator(
    private val navController: NavHostController
) : BaseCoordinator {
    override fun navigateTo(destinationId: Int) {
        navController.navigate("productDetails/$destinationId")
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
}