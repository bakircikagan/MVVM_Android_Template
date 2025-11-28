package com.example.mvvm_android_template.application.coordinator

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mvvm_android_template.application.view_model.BrochuresViewModel
import com.example.mvvm_android_template.presentation.BrochuresScreen
import javax.inject.Inject

class BrochuresRoutable @Inject constructor() : Routable {
    override val route: String = "brochures"

    override fun register(builder: NavGraphBuilder) {
        builder.composable(route) {
            val vm: BrochuresViewModel = hiltViewModel()
            BrochuresScreen(vm)
        }
    }
}
