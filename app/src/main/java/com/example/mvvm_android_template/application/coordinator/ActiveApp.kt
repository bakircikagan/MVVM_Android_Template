package com.example.mvvm_android_template.application.coordinator

import com.example.mvvm_android_template.domain.language.Language

enum class ActiveApp {
    E_COMMERCE,
    BROCHURES;

    /**
     * Get localized label for this app
     */
    fun getLabel(language: Language): String {
        return when (this) {
            E_COMMERCE -> when (language) {
                Language.TR -> "E-Ticaret"
                Language.EN -> "E-Commerce"
                Language.AR -> "التجارة الإلكترونية"
            }
            BROCHURES -> when (language) {
                Language.TR -> "Broşürler"
                Language.EN -> "Brochures"
                Language.AR -> "كتيبات"
            }
        }
    }
}
