package com.example.mvvm_android_template.application.view_model.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_android_template.infrastructure.FakeProductRepository
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.application.coordinator.BaseCoordinator

class ProductsViewModelFactory(
    private val languageSubject: LanguageSubject,
    private val coordinator: BaseCoordinator,
    private val repository: FakeProductRepository = FakeProductRepository()
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(
                repository = repository,
                languageSubject = languageSubject,
                coordinator = coordinator
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}
