package com.example.mvvm_android_template.data

import com.example.mvvm_android_template.domain.model.language.Language
import com.example.mvvm_android_template.domain.model.Product

class FakeProductRepository : ProductRepository {

    override suspend fun getProducts(language: Language): List<Product> =
        when (language) {
            Language.TR -> listOf(
                Product("Laptop", "Elektronik"),
                Product("Tişört", "Giyim"),
                Product("Kahve Bardağı", "Mutfak"),
                Product("Gaming Mouse", "Elektronik"),
                Product("Kot Pantolon", "Giyim"),
                Product("Blender", "Mutfak")
            )

            Language.EN -> listOf(
                Product("Laptop", "Electronics"),
                Product("T-Shirt", "Clothing"),
                Product("Coffee Mug", "Kitchen"),
                Product("Gaming Mouse", "Electronics"),
                Product("Jeans", "Clothing"),
                Product("Blender", "Kitchen")
            )

            Language.AR -> listOf(
                Product("لابتوب", "إلكترونيات"),
                Product("تيشيرت", "ملابس"),
                Product("كوب قهوة", "مطبخ"),
                Product("ماوس ألعاب", "إلكترونيات"),
                Product("بنطال جينز", "ملابس"),
                Product("خلاط", "مطبخ")
            )
        }
}