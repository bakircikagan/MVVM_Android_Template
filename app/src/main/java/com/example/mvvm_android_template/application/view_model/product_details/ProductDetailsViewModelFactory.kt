package com.example.mvvm_android_template.application.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_android_template.data.FakeProductRepository
import com.example.mvvm_android_template.domain.model.language.LanguageSubject
import com.example.mvvm_android_template.application.coordinator.BaseCoordinator

class ProductDetailsViewModelFactory(
    private val languageSubject: LanguageSubject,
    private val productId: Int,
    private val coordinator: BaseCoordinator,
    private val repository: FakeProductRepository = FakeProductRepository()
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            return ProductDetailsViewModel(
                repository = repository,
                languageSubject = languageSubject,
                productId = productId,
                coordinator = coordinator
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}
