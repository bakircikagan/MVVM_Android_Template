package com.example.mvvm_android_template.application.coordinator

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mvvm_android_template.application.view_model.ProductDetailsViewModel
import com.example.mvvm_android_template.presentation.ProductDetailsScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDetailsRoutable @Inject constructor() : Routable {

    override val route: String = "productDetails/{productId}"

    override fun register(builder: NavGraphBuilder) {
        builder.composable(
            route = route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            val viewModel: ProductDetailsViewModel = hiltViewModel()
            ProductDetailsScreen(viewModel = viewModel)
        }
    }
}
