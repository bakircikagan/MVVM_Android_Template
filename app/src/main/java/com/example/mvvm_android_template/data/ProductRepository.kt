package com.example.mvvm_android_template.data

import com.example.mvvm_android_template.domain.model.language.Language
import com.example.mvvm_android_template.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(language: Language): List<Product>
}