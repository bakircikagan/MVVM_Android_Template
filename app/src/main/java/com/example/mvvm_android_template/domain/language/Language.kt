package com.example.mvvm_android_template.domain.language

enum class Language { TR, EN, AR }

fun localizeTitle(language: Language): String =
    when (language) {
        Language.TR -> "Ürünler"
        Language.EN -> "Products"
        Language.AR -> "المنتجات"
    }
fun isLtrLanguage(language: Language): Boolean = language != Language.AR