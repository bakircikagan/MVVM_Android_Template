package com.example.mvvm_android_template.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mvvm_android_template.domain.Brochure
import com.example.mvvm_android_template.domain.language.Language
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BrochureCard(
    brochure: Brochure,
    language: Language,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val title = brochure.title[language] ?: brochure.title.values.firstOrNull().orEmpty()
    val description = brochure.description[language] ?: brochure.description.values.firstOrNull().orEmpty()
    val category = brochure.category[language] ?: brochure.category.values.firstOrNull().orEmpty()

    val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val publishedText = dateFormatter.format(brochure.publishedDate)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Thumbnail
            AsyncImage(
                model = brochure.imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Category "chip"
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Published date
                    Text(
                        text = publishedText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
