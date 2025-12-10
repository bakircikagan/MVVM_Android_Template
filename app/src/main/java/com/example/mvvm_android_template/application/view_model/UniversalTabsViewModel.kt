package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_android_template.application.coordinator.ActiveApp
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.application.coordinator.Route
import com.example.mvvm_android_template.application.coordinator.RouteDiscovery
import com.example.mvvm_android_template.application.event.BrochuresEvent
import com.example.mvvm_android_template.application.event.EventHandler
import com.example.mvvm_android_template.application.event.UniversalBottomBarEvent
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
) : ViewModel(), LanguageObserver , EventHandler<UniversalBottomBarEvent>{

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
     * Set which app's tabs to display
     */
    private fun setApp(app: ActiveApp) {
        currentActivity = app
        rebuildItems(app, languageSubject.currentLanguage)
    }

    private fun rebuildItems(app: ActiveApp, language: Language) {
        val tabs = routeDiscovery.getTabsForApp(app, language)
        _items.value = if (isLtrLanguage(language)) tabs else tabs.reversed()
    }
    private fun onTabSelected(route: String) {
        viewModelScope.launch {
            when {
                route.startsWith(Route.Welcome.path) -> {
                    coordinator.navigate(Route.Welcome, clearStack = true)
                }

                route.startsWith(Route.Products.path) -> {
                    coordinator.navigate(Route.Products, clearStack = true)
                }

                route.startsWith(Route.Brochures.path) -> {
                    coordinator.navigate(Route.Brochures, clearStack = true)
                }

                route.startsWith(Route.Categories.path) -> {
                    coordinator.navigate(Route.Categories, clearStack = true)
                }

                // add any other top-level tab routes here
            }
        }
    }

    override fun onEvent(event: UniversalBottomBarEvent) {
        when(event) {
            is UniversalBottomBarEvent.TabSelected -> onTabSelected(event.route)
            is UniversalBottomBarEvent.AppChanged -> setApp(event.app)
        }
    }
}
