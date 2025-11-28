package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppSwitcherViewModel @Inject constructor() : ViewModel() {

    private val _activeApp = MutableLiveData(ActiveApp.E_COMMERCE)
    val activeApp: LiveData<ActiveApp> get() = _activeApp

    fun switchApp(app: ActiveApp) {
        _activeApp.value = app
    }
}

enum class ActiveApp {
    E_COMMERCE,
    BROCHURES
}
