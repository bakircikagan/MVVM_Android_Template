package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_android_template.infrastructure.FakeProductRepository
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.application.coordinator.NavCommand
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.domain.language.isLtrLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: FakeProductRepository,
    private val languageSubject: LanguageSubject,
    private val coordinator: Coordinator,
    savedStateHandle: SavedStateHandle
) : ViewModel(), LanguageObserver {

    private val productId: Int = savedStateHandle["productId"]
        ?: error("productId is required")


    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _category = MutableLiveData<String>()
    val category: LiveData<String> = _category

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

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
        _isLoading.value = true

        viewModelScope.launch {
            val products = repository.getProducts(language)
            val product = products.firstOrNull { it.id == productId }

            if (product != null) {
                _name.value = product.name
                _category.value = product.category
                _description.value = product.description
            } else {
                _name.value = ""
                _category.value = ""
                _description.value = ""
            }

            _isLoading.value = false
        }
    }

    fun onBack() {
        viewModelScope.launch {
            coordinator.navigate(NavCommand.Back)
        }
    }

    fun onLanguageSelected(language: Language) {
        languageSubject.setLanguage(language)
    }
}
