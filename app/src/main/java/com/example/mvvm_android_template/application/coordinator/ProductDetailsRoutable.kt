package com.example.mvvm_android_template.presentation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mvvm_android_template.application.coordinator.LocalMainCoordinator
import com.example.mvvm_android_template.application.coordinator.MainCoordinator
import com.example.mvvm_android_template.application.coordinator.Routable
import com.example.mvvm_android_template.application.view_model.LanguageViewModel
import com.example.mvvm_android_template.application.view_model.product_details.ProductDetailsViewModel
import com.example.mvvm_android_template.application.view_model.product_details.ProductDetailsViewModelFactory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDetailsRoutable @Inject constructor() : Routable {

    override val route: String = "productDetails/{productId}"

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(
            route = route,                                                                                                        
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable

            val languageViewModel: LanguageViewModel = viewModel()
            val coordinator: MainCoordinator = LocalMainCoordinator.current

            val detailsViewModel: ProductDetailsViewModel = viewModel(
                factory = ProductDetailsViewModelFactory(
                    languageSubject = languageViewModel,
                    productId = productId,
                    coordinator = coordinator
                )
            )

            ProductDetailsScreen(viewModel = detailsViewModel)
        }
    }
}
