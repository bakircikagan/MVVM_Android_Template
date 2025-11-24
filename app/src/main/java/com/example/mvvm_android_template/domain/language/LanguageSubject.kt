package com.example.mvvm_android_template.domain.model.language


interface LanguageSubject {
    val observers: MutableSet<LanguageObserver>
    var currentLanguage: Language

    fun addObserver(observer: LanguageObserver) {
        observers.add(observer)
        observer.onLanguageChanged(currentLanguage)
    }

    fun removeObserver(observer: LanguageObserver) {
        observers.remove(observer)
    }

    fun setLanguage(language: Language) {
        if (language != currentLanguage) {
            currentLanguage = language
            notifyObservers(language)
        }
    }

    fun notifyObservers(language: Language) {
        observers.forEach { it.onLanguageChanged(language) }
    }
}
