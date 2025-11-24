package com.example.mvvm_android_template.presentation.language

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mvvm_android_template.domain.language.Language

@Composable
fun LanguageView(
    selected: Language,
    isLtr: Boolean,
    onLanguageSelected: (Language) -> Unit
) {
    val options = if (isLtr) {
        listOf(Language.TR, Language.EN, Language.AR)
    } else {
        listOf(Language.AR, Language.EN, Language.TR)
    }

    val labels = mapOf(
        Language.TR to "TR",
        Language.EN to "EN",
        Language.AR to "AR"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(Color(0xFF202020)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { language ->
            val isSelected = language == selected

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(2.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(if (isSelected) Color(0xFF3A3A3A) else Color.Transparent)
                    .clickable { onLanguageSelected(language) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = labels[language] ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) Color.White else Color(0xFFB0B0B0),
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

