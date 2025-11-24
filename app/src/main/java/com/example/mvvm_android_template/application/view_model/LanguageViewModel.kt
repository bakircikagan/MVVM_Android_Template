package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.ViewModel
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject

class LanguageViewModel: ViewModel(), LanguageSubject {
    override val observers = mutableSetOf<LanguageObserver>()
    override var currentLanguage: Language = Language.TR
}