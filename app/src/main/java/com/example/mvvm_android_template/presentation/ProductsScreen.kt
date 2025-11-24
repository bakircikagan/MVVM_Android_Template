package com.example.mvvm_android_template.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.example.mvvm_android_template.application.view_model.products.ProductsViewModel
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.localizeTitle

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel
) {
    // 🔹 bind to row view-models, not domain Product
    val rows by viewModel.items.observeAsState(emptyList())
    val title by viewModel.title.observeAsState(localizeTitle(Language.TR))
    val isLtr by viewModel.isLtr.observeAsState(false)
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()
    val selectedLanguage by viewModel.selectedLanguage.observeAsState(Language.TR)

    DirectionalScreen(isLtr = isLtr) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LanguageView(
                selected = selectedLanguage,
                isLtr = isLtr,
                onLanguageSelected = { language ->
                    viewModel.onLanguageSelected(language)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                }

                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = errorMessage ?: "", color = Color.Red)
                    }
                }

                else -> {
                    LazyColumn {
                        items(
                            count = rows.size,
                            key = { index -> rows[index].id }
                        ) { index ->
                            val rowVm = rows[index]
                            ProductView(viewModel = rowVm)
                        }
                    }
                }
            }
        }
    }
}
