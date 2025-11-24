package com.example.mvvm_android_template.domain

import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.localizeTitle

data class Products(
    val selectedLanguage: Language = Language.TR,
    val title: String = localizeTitle(selectedLanguage),
    val isLtr: Boolean = false,
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
