package com.example.mvvm_android_template.presentation.product_details

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
import com.example.mvvm_android_template.presentation.DirectionalScreen
import com.example.mvvm_android_template.presentation.language.LanguageView

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel
) {
    val product by viewModel.product.observeAsState()
    val selectedLanguage by viewModel.selectedLanguage.observeAsState(Language.TR)
    val isLtr by viewModel.isLtr.observeAsState(false)

    DirectionalScreen(isLtr = isLtr) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            // language selector – view just forwards the event
            LanguageView(
                selected = selectedLanguage,
                isLtr = isLtr,
                onLanguageSelected = { lang ->
                    viewModel.onLanguageSelected(lang)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Back – view just forwards click
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

            if (product == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading...",
                        color = Color.Gray
                    )
                }
            } else {
                Text(
                    text = product!!.name,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product!!.category,
                    color = Color(0xFFB0B0B0),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = product!!.description,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 22.sp
                )
            }
        }
    }
}
