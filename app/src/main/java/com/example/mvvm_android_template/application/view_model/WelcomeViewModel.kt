package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_android_template.domain.WelcomeTexts
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.domain.language.isLtrLanguage
import com.example.mvvm_android_template.infrastructure.WelcomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val languageSubject: LanguageSubject,
    private val welcomeRepository: WelcomeRepository
) : ViewModel(), LanguageObserver {

    private val _selectedLanguage = MutableLiveData(languageSubject.currentLanguage)
    val selectedLanguage: LiveData<Language> = _selectedLanguage

    private val _isLtr = MutableLiveData(isLtrLanguage(languageSubject.currentLanguage))
    val isLtr: LiveData<Boolean> = _isLtr

    private val _texts = MutableLiveData(
        welcomeRepository.getTexts(languageSubject.currentLanguage)
    )
    val texts: LiveData<WelcomeTexts> = _texts

    init {
        languageSubject.addObserver(this)
    }

    override fun onLanguageChanged(language: Language) {
        _selectedLanguage.value = language
        _isLtr.value = isLtrLanguage(language)
        _texts.value = welcomeRepository.getTexts(language)
    }

    override fun onCleared() {
        super.onCleared()
        languageSubject.removeObserver(this)
    }

    fun onLanguageSelected(language: Language) {
        languageSubject.setLanguage(language)
    }
}
