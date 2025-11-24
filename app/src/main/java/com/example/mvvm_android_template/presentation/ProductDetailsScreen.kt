package com.example.mvvm_android_template.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvm_android_template.application.view_model.product_details.ProductDetailsViewModel

import com.example.mvvm_android_template.domain.language.Language

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel
) {
    val name by viewModel.name.observeAsState("")
    val category by viewModel.category.observeAsState("")
    val description by viewModel.description.observeAsState("")
    val selectedLanguage by viewModel.selectedLanguage.observeAsState(Language.TR)
    val isLtr by viewModel.isLtr.observeAsState(false)
    val isLoading by viewModel.isLoading.observeAsState(true)

    DirectionalScreen(isLtr = isLtr) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            LanguageView(
                selected = selectedLanguage,
                isLtr = isLtr,
                onLanguageSelected = { lang -> viewModel.onLanguageSelected(lang) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (isLtr) Arrangement.Start else Arrangement.End
            ) {
                Text(
                    text = "← Back",
                    modifier = Modifier
                        .clickable { viewModel.onBack() }
                        .padding(vertical = 8.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Loading...", color = Color.Gray)
                    }
                }

                name.isEmpty() -> { // product not found
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Product not found", color = Color.Gray)
                    }
                }

                else -> {
                    Text(
                        text = name,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = category,
                        color = Color(0xFFF60202),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = description,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}
