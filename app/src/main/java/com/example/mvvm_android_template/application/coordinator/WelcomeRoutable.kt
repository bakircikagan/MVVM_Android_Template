package com.example.mvvm_android_template.application.coordinator

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvm_android_template.application.view_model.WelcomeViewModel
import com.example.mvvm_android_template.presentation.WelcomeScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WelcomeRoutable @Inject constructor() : Routable {

    override val route: String = "welcome"

    override fun register(builder: NavGraphBuilder) {
        builder.composable(route) {
            val vm: WelcomeViewModel = hiltViewModel()
            WelcomeScreen(viewModel = vm)
        }
    }
}
