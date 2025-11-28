package com.example.mvvm_android_template.infrastructure

import com.example.mvvm_android_template.domain.WelcomeTexts
import com.example.mvvm_android_template.domain.language.Language

class WelcomeRepository {
    fun getTexts(language: Language): WelcomeTexts {
        return when (language) {
            Language.TR -> WelcomeTexts(
                title = "Hoş Geldiniz",
                subtitle = "Dil seçin ve ürünleri keşfedin."
            )
            Language.EN -> WelcomeTexts(
                title = "Welcome",
                subtitle = "Select your language and explore the products."
            )
            Language.AR -> WelcomeTexts(
                title = "مرحبا",
                subtitle = "اختر لغتك واستكشف المنتجات."
            )
        }
    }
}