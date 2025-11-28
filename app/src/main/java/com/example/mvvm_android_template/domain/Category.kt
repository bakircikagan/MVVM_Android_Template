package com.example.mvvm_android_template.domain

import com.example.mvvm_android_template.domain.language.Language

data class Category(
    val id: Int,
    val name: Map<Language, String>,
    val description: Map<Language, String>,
    val iconName: String,
    val brochureCount: Int
)
