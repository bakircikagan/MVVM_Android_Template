package com.example.mvvm_android_template.domain.model.language

class LanguageSelectionSubject(
    initialLanguage: Language = Language.TR
) : LanguageSubject {

    private val observers = mutableSetOf<LanguageObserver>()

    private var currentLanguage: Language = initialLanguage

    override fun addObserver(observer: LanguageObserver) {
        observers.add(observer)
        observer.onLanguageChanged(currentLanguage)
    }

    override fun removeObserver(observer: LanguageObserver) {
        observers.remove(observer)
    }

    override fun setLanguage(language: Language) {
        if (language == currentLanguage) return

        currentLanguage = language

        observers.forEach { it.onLanguageChanged(language) }
    }

    override fun getCurrentLanguage(): Language = currentLanguage
}