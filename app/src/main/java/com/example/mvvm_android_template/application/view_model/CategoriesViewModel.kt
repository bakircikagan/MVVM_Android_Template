package com.example.mvvm_android_template.application.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_android_template.application.coordinator.Coordinator
import com.example.mvvm_android_template.domain.Category
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.LanguageObserver
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.infrastructure.FakeCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val languageSubject: LanguageSubject,
    private val coordinator: Coordinator,
    private val categoryRepository: FakeCategoryRepository
) : ViewModel(), LanguageObserver {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _language = MutableLiveData(languageSubject.currentLanguage)
    val language: LiveData<Language> get() = _language

    init {
        languageSubject.addObserver(this)
        loadCategories()
    }

    override fun onCleared() {
        super.onCleared()
        languageSubject.removeObserver(this)
    }

    override fun onLanguageChanged(language: Language) {
        _language.value = language
        loadCategories()
    }

    private fun loadCategories() {
        _categories.value = categoryRepository.getCategories()
    }

    fun onCategorySelected(categoryId: Int) {
        // In the future, you could navigate to a category details screen
        // For now, just log or handle the selection
    }
}
