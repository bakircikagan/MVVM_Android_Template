package com.example.mvvm_android_template.application.view_model

data class ProductRowViewModel(
    val id: Int,
    val name: String,
    val category: String,
    val onClick: () -> Unit
)