package com.example.mvvm_android_template.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.mvvm_android_template.domain.Products
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.application.coordinator.BaseCoordinator
import com.example.mvvm_android_template.application.coordinator.MainCoordinator
import com.example.mvvm_android_template.application.view_model.LanguageViewModel
import com.example.mvvm_android_template.application.view_model.product_details.ProductDetailsViewModel
import com.example.mvvm_android_template.application.view_model.product_details.ProductDetailsViewModelFactory
import com.example.mvvm_android_template.application.view_model.products.ProductsViewModel
import com.example.mvvm_android_template.application.view_model.products.ProductsViewModelFactory
import com.example.mvvm_android_template.domain.language.localizeTitle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    val coordinator = remember(navController) {
        MainCoordinator(navController)
    }

    val languageViewModel: LanguageViewModel = viewModel()

    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            NavHost(
                navController = navController,
                startDestination = "products"
            ) {
                composable("products") {
                    val productsViewModel: ProductsViewModel = viewModel(
                        factory = ProductsViewModelFactory(
                            languageSubject = languageViewModel,
                            coordinator = coordinator
                        )
                    )

                    // 🔹 Directly use screen composable
                    ProductsScreen(viewModel = productsViewModel)
                }

                composable(
                    route = "productDetails/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->

                    val productId =
                        backStackEntry.arguments?.getInt("productId") ?: return@composable

                    val detailsViewModel: ProductDetailsViewModel = viewModel(
                        factory = ProductDetailsViewModelFactory(
                            languageSubject = languageViewModel,
                            productId = productId,
                            coordinator = coordinator
                        )
                    )

                    // 🔹 Directly use screen composable
                    ProductDetailsScreen(viewModel = detailsViewModel)
                }
            }
        }
    }
}
