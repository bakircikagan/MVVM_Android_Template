package com.example.mvvm_android_template.domain.language

import javax.inject.Inject

class InMemoryLanguageSubject @Inject constructor() : LanguageSubject {
    override val observers = mutableSetOf<LanguageObserver>()
    override var currentLanguage: Language = Language.TR
}
