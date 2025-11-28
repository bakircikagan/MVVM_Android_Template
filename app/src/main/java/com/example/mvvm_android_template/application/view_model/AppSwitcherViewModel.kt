package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_android_template.application.coordinator.RouteDiscovery
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppSwitcherViewModel @Inject constructor(
    private val routeDiscovery: RouteDiscovery,
    private val languageSubject: LanguageSubject
) : ViewModel(), LanguageObserver {

    private val _activeApp = MutableLiveData<ActiveApp>()
    val activeApp: LiveData<ActiveApp> get() = _activeApp

    private val _availableApps = MutableLiveData<List<ActiveApp>>()
    val availableApps: LiveData<List<ActiveApp>> get() = _availableApps

    private val _language = MutableLiveData(languageSubject.currentLanguage)
    val language: LiveData<Language> get() = _language

    init {
        languageSubject.addObserver(this)
        loadAvailableApps()
    }

    override fun onCleared() {
        super.onCleared()
        languageSubject.removeObserver(this)
    }

    override fun onLanguageChanged(language: Language) {
        _language.value = language
    }

    private fun loadAvailableApps() {
        // Automatically discover all apps from routes
        val apps = routeDiscovery.getActivities()
        _availableApps.value = apps

        // Set initial active app to first discovered app
        if (_activeApp.value == null && apps.isNotEmpty()) {
            _activeApp.value = apps.first()
        }
    }

    fun switchApp(app: ActiveApp) {
        _activeApp.value = app
    }
}

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
