package com.example.mvvm_android_template.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

import com.example.mvvm_android_template.data.FakeProductRepository
import com.example.mvvm_android_template.domain.model.Product
import com.example.mvvm_android_template.domain.model.isLtrLanguage
import com.example.mvvm_android_template.domain.model.language.Language
import com.example.mvvm_android_template.domain.model.language.LanguageObserver
import com.example.mvvm_android_template.domain.model.language.LanguageSubject
import com.example.mvvm_android_template.domain.model.localizeTitle
import com.example.mvvm_android_template.application.coordinator.BaseCoordinator

class ProductsViewModel(
    private val repository: FakeProductRepository,
    private val languageSubject: LanguageSubject,
    private val coordinator: BaseCoordinator
) : ViewModel(), LanguageObserver {

    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products

    private val _title = MutableLiveData<String>(localizeTitle(languageSubject.currentLanguage))
    val title: LiveData<String> = _title

    private val _isLtr = MutableLiveData<Boolean>(isLtrLanguage(languageSubject.currentLanguage))
    val isLtr: LiveData<Boolean> = _isLtr

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _selectedLanguage = MutableLiveData<Language>(languageSubject.currentLanguage)
    val selectedLanguage: LiveData<Language> = _selectedLanguage

    init {
        languageSubject.addObserver(this)
        loadProducts(languageSubject.currentLanguage)
    }

    override fun onCleared() {
        super.onCleared()
        languageSubject.removeObserver(this)
    }

    override fun onLanguageChanged(language: Language) {
        _selectedLanguage.value = language
        _isLtr.value = isLtrLanguage(language)
        _title.value = localizeTitle(language)
        loadProducts(language)
    }

    private fun loadProducts(language: Language) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val list = repository.getProducts(language)
                _products.value = list
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message ?: "Unknown error"
            }
        }
    }

    // 🔹 UI events:

    fun onProductClicked(product: Product) {
        coordinator.navigateTo(product.id)
    }

    fun onLanguageSelected(language: Language) {
        languageSubject.setLanguage(language)
    }
}

