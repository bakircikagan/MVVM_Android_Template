package com.example.mvvm_android_template.presentation.coordinator

interface BaseCoordinator {
    fun navigateTo(destinationId: Int)
    fun navigateBack()
}