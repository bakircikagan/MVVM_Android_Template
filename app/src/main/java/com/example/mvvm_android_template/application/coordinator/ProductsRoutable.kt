package com.example.mvvm_android_template.presentation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.example.mvvm_android_template.application.coordinator.LocalMainCoordinator
import com.example.mvvm_android_template.application.coordinator.MainCoordinator
import com.example.mvvm_android_template.application.coordinator.Routable
import com.example.mvvm_android_template.application.view_model.LanguageViewModel
import com.example.mvvm_android_template.application.view_model.products.ProductsViewModel
import com.example.mvvm_android_template.application.view_model.products.ProductsViewModelFactory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRoutable @Inject constructor() : Routable {

    override val route: String = "products"

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) { backStackEntry ->

            // Shared language VM
            val languageViewModel: LanguageViewModel = viewModel()

            // Coordinator from CompositionLocal
            val coordinator: MainCoordinator = LocalMainCoordinator.current

            // Feature VM
            val productsViewModel: ProductsViewModel = viewModel(
                factory = ProductsViewModelFactory(
                    languageSubject = languageViewModel,
                    coordinator = coordinator
                )
            )

            ProductsScreen(viewModel = productsViewModel)
        }
    }
}
