package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

import com.example.mvvm_android_template.infrastructure.FakeProductRepository
import com.example.mvvm_android_template.domain.Product
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.application.coordinator.NavCommand
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.domain.language.isLtrLanguage
import com.example.mvvm_android_template.domain.language.localizeTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: FakeProductRepository,
    private val languageSubject: LanguageSubject,
    private val coordinator: Coordinator
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
        viewModelScope.launch {
            val list = repository.getProducts(language)
            _products.postValue(list)
        }
    }

    // 🔹 UI events:
    fun onProductClicked(productId: Int) {
        viewModelScope.launch {
            coordinator.navigate(NavCommand.ToProductDetails(productId))
        }
    }

    fun onLanguageSelected(language: Language) {
        languageSubject.setLanguage(language)
    }
}

