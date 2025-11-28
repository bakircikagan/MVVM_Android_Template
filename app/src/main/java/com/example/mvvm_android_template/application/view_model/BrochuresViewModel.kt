package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.application.coordinator.NavCommand
import com.example.mvvm_android_template.domain.Brochure
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.infrastructure.FakeBrochureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrochuresViewModel @Inject constructor(
    private val languageSubject: LanguageSubject,
    private val coordinator: Coordinator
) : ViewModel(), LanguageObserver {

    // 🚫 not injected, just created locally
    private val repo = FakeBrochureRepository()

    private val _brochures = MutableLiveData<List<Brochure>>()
    val brochures: LiveData<List<Brochure>> get() = _brochures

    private val _language = MutableLiveData(languageSubject.currentLanguage)
    val language: LiveData<Language> get() = _language

    init {
        languageSubject.addObserver(this)
        onLanguageChanged(languageSubject.currentLanguage)
    }

    override fun onLanguageChanged(language: Language) {
        _language.value = language
        _brochures.value = repo.getBrochures(language)
    }

    fun onBrochureSelected(id: Int) {
        viewModelScope.launch {
            coordinator.navigate(NavCommand.ToBrochureDetails(id))
        }
    }
}
