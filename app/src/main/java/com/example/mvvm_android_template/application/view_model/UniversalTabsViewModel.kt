package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.application.coordinator.RouteDiscovery
import com.example.mvvm_android_template.domain.BottomTabItem
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.domain.language.isLtrLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Universal ViewModel for bottom navigation tabs.
 * Automatically discovers and builds tabs for any activity using RouteDiscovery.
 */
@HiltViewModel
class UniversalTabsViewModel @Inject constructor(
    private val languageSubject: LanguageSubject,
    private val coordinator: Coordinator,
    private val routeDiscovery: RouteDiscovery
) : ViewModel(), LanguageObserver {

    private val _items = MutableLiveData<List<BottomTabItem>>()
    val items: LiveData<List<BottomTabItem>> = _items

    private var currentActivity: ActiveApp = ActiveApp.E_COMMERCE

    init {
        languageSubject.addObserver(this)
    }

    override fun onCleared() {
        super.onCleared()
        languageSubject.removeObserver(this)
    }

    override fun onLanguageChanged(language: Language) {
        rebuildItems(currentActivity, language)
    }

    /**
     * Set which activity's tabs to display
     */
    fun setActivity(activity: ActiveApp) {
        currentActivity = activity
        rebuildItems(activity, languageSubject.currentLanguage)
    }

    private fun rebuildItems(activity: ActiveApp, language: Language) {
        val tabs = routeDiscovery.getTabsForActivity(activity, language)
        _items.value = if (isLtrLanguage(language)) tabs else tabs.reversed()
    }

    fun onTabSelected(route: String) {
        viewModelScope.launch {
            routeDiscovery.getNavCommandForRoute(route)?.let {
                coordinator.navigate(it)
            }
        }
    }
}
