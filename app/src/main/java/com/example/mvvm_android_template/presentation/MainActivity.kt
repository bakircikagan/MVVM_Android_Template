package com.example.mvvm_android_template.presentation.products

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

import com.example.mvvm_android_template.domain.model.Product
import com.example.mvvm_android_template.domain.model.Products
import com.example.mvvm_android_template.domain.model.language.Language
import com.example.mvvm_android_template.domain.model.language.LanguageSubject
import com.example.mvvm_android_template.domain.model.localizeTitle
import com.example.mvvm_android_template.presentation.coordinator.BaseCoordinator
import com.example.mvvm_android_template.presentation.coordinator.MainCoordinator
import com.example.mvvm_android_template.presentation.language.LanguageViewModel
import com.example.mvvm_android_template.presentation.product_details.ProductDetailsView
import com.example.mvvm_android_template.presentation.product_details.ProductDetailsViewModel
import com.example.mvvm_android_template.presentation.product_details.ProductDetailsViewModelFactory

class ProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductsApp()
        }
    }
}

@Composable
fun ProductsApp() {
    val navController = rememberNavController()

    // Coordinator drives navigation, NOT the composables
    val coordinator = remember(navController) {
        MainCoordinator(navController)
    }

    // Shared ViewModel across whole app
    val languageViewModel: LanguageViewModel = viewModel()

    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            // 🔥 THIS IS YOUR NAVHOST (THE ROOT OF YOUR NAVIGATION)
            NavHost(
                navController = navController,
                startDestination = "products"
            ) {
                composable("products") {
                    val productsViewModel: ProductsViewModel = viewModel(
                        factory = ProductsViewModelFactory(languageViewModel)
                    )

                    ProductsRoute(
                        productsViewModel = productsViewModel,
                        languageViewModel = languageViewModel,
                        coordinator = coordinator
                    )
                }

                composable(
                    route = "productDetails/{productName}",
                    arguments = listOf(
                        navArgument("productName") { type = NavType.StringType }
                    )
                ) { backStackEntry ->

                    val productName =
                        backStackEntry.arguments?.getString("productName") ?: return@composable

                    val detailsViewModel: ProductDetailsViewModel = viewModel(
                        factory = ProductDetailsViewModelFactory(
                            languageSubject = languageViewModel,
                            productName = productName,
                            coordinator = coordinator
                        )
                    )

                    ProductDetailsRoute(
                        viewModel = detailsViewModel,
                        languageSubject = languageViewModel
                    )
                }
            }
        }
    }
}



@Composable
fun ProductsRoute(
    productsViewModel: ProductsViewModel,
    languageViewModel: LanguageViewModel,
    coordinator: BaseCoordinator
) {
    val products by productsViewModel.products.observeAsState(emptyList())
    val title by productsViewModel.title.observeAsState(localizeTitle(Language.TR))
    val isLtr by productsViewModel.isLtr.observeAsState(false)
    val isLoading by productsViewModel.isLoading.observeAsState(false)
    val errorMessage by productsViewModel.errorMessage.observeAsState()
    val selectedLanguage by productsViewModel.selectedLanguage.observeAsState(Language.TR)

    val state = Products(
        selectedLanguage = selectedLanguage,
        title = title,
        isLtr = isLtr,
        products = products,
        isLoading = isLoading,
        errorMessage = errorMessage
    )

    ProductsView(
        state = state,
        languageViewModel = languageViewModel,
        onProductClick = { product ->
            coordinator.navigateTo(product.name)
        }
    )
}


@Composable
fun ProductDetailsRoute(
    viewModel: ProductDetailsViewModel,
    languageSubject: LanguageSubject
) {
    val product by viewModel.product.observeAsState()
    val selectedLanguage by viewModel.selectedLanguage.observeAsState(Language.TR)
    val isLtr by viewModel.isLtr.observeAsState(false)

    ProductDetailsView(
        product = product,
        selectedLanguage = selectedLanguage,
        isLtr = isLtr,
        viewModel = viewModel,
        languageSubject = languageSubject
    )
}
