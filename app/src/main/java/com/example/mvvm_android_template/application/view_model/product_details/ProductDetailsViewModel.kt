package com.example.mvvm_android_template.application.product_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_android_template.data.FakeProductRepository
import com.example.mvvm_android_template.domain.model.Product
import com.example.mvvm_android_template.domain.model.isLtrLanguage
import com.example.mvvm_android_template.domain.model.language.Language
import com.example.mvvm_android_template.domain.model.language.LanguageObserver
import com.example.mvvm_android_template.domain.model.language.LanguageSubject
import com.example.mvvm_android_template.application.coordinator.BaseCoordinator
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val repository: FakeProductRepository,
    private val languageSubject: LanguageSubject,
    private val productId: Int,
    private val coordinator: BaseCoordinator
) : ViewModel(), LanguageObserver {

    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> = _product

    private val _selectedLanguage = MutableLiveData<Language>(languageSubject.currentLanguage)
    val selectedLanguage: LiveData<Language> = _selectedLanguage

    private val _isLtr = MutableLiveData<Boolean>(isLtrLanguage(languageSubject.currentLanguage))
    val isLtr: LiveData<Boolean> = _isLtr

    init {
        languageSubject.addObserver(this)
        load(languageSubject.currentLanguage)
    }

    override fun onCleared() {
        super.onCleared()
        languageSubject.removeObserver(this)
    }

    override fun onLanguageChanged(language: Language) {
        _selectedLanguage.value = language
        _isLtr.value = isLtrLanguage(language)
        load(language)
    }

    private fun load(language: Language) {
        viewModelScope.launch {
            val products = repository.getProducts(language)
            _product.value = products.firstOrNull { it.id == productId }
        }
    }

    fun onBack() {
        coordinator.navigateBack()
    }

    fun onLanguageSelected(language: Language) {
        languageSubject.setLanguage(language)
        // onLanguageChanged will handle the rest
    }
}
