package com.example.mvvm_android_template.domain

import com.example.mvvm_android_template.domain.language.Language
import java.util.Date

data class Brochure(
    val id: Int,
    val title: Map<Language, String>,
    val description: Map<Language, String>,
    val category: Map<Language, String>,
    val imageUrl: String,
    val pdfUrl: String,
    val publishedDate: Date
)
