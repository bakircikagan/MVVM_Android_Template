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
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            ProductsRoute()
        }
    }
}

@Composable
fun ProductsRoute(
    viewModel: ProductsViewModel = viewModel()
) {
    val state by viewModel.uiState

    ProductsScreen(
        state = state,
        onLanguageChange = { viewModel.onLanguageSelected(it) },
        onProductClick = { product ->
            // TODO: navigate to detail screen
        }
    )
}