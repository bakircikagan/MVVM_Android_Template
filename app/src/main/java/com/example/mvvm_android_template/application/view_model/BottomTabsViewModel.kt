package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.application.coordinator.NavCommand
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.domain.language.isLtrLanguage
import com.example.mvvm_android_template.domain.BottomTabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import com.example.mvvm_android_template.application.coordinator.Route

@HiltViewModel
class BottomTabsViewModel @Inject constructor(
    private val languageSubject: LanguageSubject,
    private val coordinator: Coordinator
) : ViewModel(), LanguageObserver {

    private val _items = MutableLiveData<List<BottomTabItem>>()
    val items: LiveData<List<BottomTabItem>> = _items

    init {
        languageSubject.addObserver(this)
        rebuildItems(languageSubject.currentLanguage)
    }

    override fun onCleared() {
        super.onCleared()
        languageSubject.removeObserver(this)
    }

    override fun onLanguageChanged(language: Language) {
        rebuildItems(language)
    }

    private fun rebuildItems(language: Language) {
        val welcomeLabel = when (language) {
            Language.TR -> "Giriş"
            Language.EN -> "Welcome"
            Language.AR -> "الرئيسية"
        }

        val productsLabel = when (language) {
            Language.TR -> "Ürünler"
            Language.EN -> "Products"
            Language.AR -> "المنتجات"
        }

        val base = listOf(
            BottomTabItem(
                route = "welcome",
                label = welcomeLabel,
                icon = Icons.Filled.Home
            ),
            BottomTabItem(
                route = "products",
                label = productsLabel,
                icon = Icons.Filled.List
            )
        )

        _items.value = if (isLtrLanguage(language)) base else base.reversed()
    }

    fun onTabSelected(route: String) {
        viewModelScope.launch {
            when {
                route.startsWith("welcome") -> coordinator.navigate(Route.Welcome)
                route.startsWith("products") -> coordinator.navigate(Route.Products)
            }
        }
    }
}

