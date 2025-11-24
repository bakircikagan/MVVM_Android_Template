package com.example.mvvm_android_template.application.coordinator

interface BaseCoordinator {
    fun navigateTo(destinationId: Int)
    fun navigateBack()
}