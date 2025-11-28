package com.example.mvvm_android_template.application.coordinator

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mvvm_android_template.application.view_model.ProductsViewModel
import com.example.mvvm_android_template.presentation.ProductsScreen
import javax.inject.Inject
import javax.inject.Singleton

//TODO: route config hangi activityde, hangi tablari var
@Singleton
class ProductsRoutable @Inject constructor() : Routable {

    override val route: String = "products"

    override fun register(builder: NavGraphBuilder) {
        builder.composable(route) {
            val viewModel: ProductsViewModel = hiltViewModel()
            ProductsScreen(viewModel = viewModel)
        }
    }
}
