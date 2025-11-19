package com.example.mvvm_android_template.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch

import com.example.mvvm_android_template.data.FakeProductRepository
import com.example.mvvm_android_template.data.ProductRepository
import com.example.mvvm_android_template.domain.model.language.Language
import com.example.mvvm_android_template.domain.model.language.LanguageObserver
import com.example.mvvm_android_template.domain.model.language.LanguageSelectionSubject
import com.example.mvvm_android_template.domain.model.language.LanguageSubject

class ProductsViewModel(
    private val repository: ProductRepository = FakeProductRepository(),
    private val languageSubject: LanguageSubject = LanguageSelectionSubject()
) : ViewModel(), LanguageObserver {

    private val _uiState = mutableStateOf(ProductsUiState())
    val uiState: State<ProductsUiState> = _uiState

    init {
        languageSubject.addObserver(this)
    }

    override fun onCleared() {
        super.onCleared()
        languageSubject.removeObserver(this)
    }

    override fun onLanguageChanged(language: Language) {
        loadProducts(language)
    }

    fun onLanguageSelected(language: Language) {
        languageSubject.setLanguage(language)
    }

    private fun loadProducts(language: Language) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                selectedLanguage = language,
                title = localizeTitle(language),
                isLtr = isLtrLanguage(language)
            )

            try {
                val products = repository.getProducts(language)
                _uiState.value = _uiState.value.copy(
                    products = products,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error"
                )
            }
        }
    }
}