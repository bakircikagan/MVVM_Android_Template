package com.example.mvvm_android_template.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvvm_android_template.application.view_model.CategoriesViewModel
import com.example.mvvm_android_template.domain.Category
import com.example.mvvm_android_template.domain.language.Language

@Composable
fun CategoriesScreen(viewModel: CategoriesViewModel) {
    val categories by viewModel.categories.observeAsState(emptyList())
    val language by viewModel.language.observeAsState(Language.TR)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            CategoryCard(
                category = category,
                language = language,
                onClick = { viewModel.onCategorySelected(category.id) }
            )
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    language: Language,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.name[language] ?: category.name[Language.EN] ?: "",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = category.description[language] ?: category.description[Language.EN] ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (language) {
                        Language.TR -> "${category.brochureCount} broşür"
                        Language.EN -> "${category.brochureCount} brochures"
                        Language.AR -> "${category.brochureCount} كتيبات"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
