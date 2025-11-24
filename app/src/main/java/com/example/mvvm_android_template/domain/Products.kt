package com.example.mvvm_android_template.domain.model

import com.example.mvvm_android_template.domain.model.language.Language

data class Products(
    val selectedLanguage: Language = Language.TR,
    val title: String = localizeTitle(selectedLanguage),
    val isLtr: Boolean = false,
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
fun localizeTitle(language: Language): String =
    when (language) {
        Language.TR -> "Ürünler"
        Language.EN -> "Products"
        Language.AR -> "المنتجات"
    }
fun isLtrLanguage(language: Language): Boolean = language != Language.AR