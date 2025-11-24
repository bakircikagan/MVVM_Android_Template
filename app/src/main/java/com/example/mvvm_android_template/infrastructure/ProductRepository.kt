package com.example.mvvm_android_template.infrastructure

import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.Product

interface ProductRepository {
    suspend fun getProducts(language: Language): List<Product>
}