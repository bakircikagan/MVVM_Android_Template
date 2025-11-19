package com.example.mvvm_android_template.domain.model.language


interface LanguageSubject {
    fun addObserver(observer: LanguageObserver)
    fun removeObserver(observer: LanguageObserver)
    fun setLanguage(language: Language)
    fun getCurrentLanguage(): Language
}