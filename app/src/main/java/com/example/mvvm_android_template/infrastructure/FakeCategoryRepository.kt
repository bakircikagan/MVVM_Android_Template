package com.example.mvvm_android_template.infrastructure

import com.example.mvvm_android_template.domain.Category
import com.example.mvvm_android_template.domain.language.Language
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeCategoryRepository @Inject constructor() {

    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = mapOf(
                    Language.TR to "Elektronik",
                    Language.EN to "Electronics",
                    Language.AR to "الإلكترونيات"
                ),
                description = mapOf(
                    Language.TR to "Teknoloji ürünleri ve aksesuarlar",
                    Language.EN to "Technology products and accessories",
                    Language.AR to "منتجات التكنولوجيا والإكسسوارات"
                ),
                iconName = "electronics",
                brochureCount = 15
            ),
            Category(
                id = 2,
                name = mapOf(
                    Language.TR to "Giyim",
                    Language.EN to "Clothing",
                    Language.AR to "ملابس"
                ),
                description = mapOf(
                    Language.TR to "Moda ve tekstil ürünleri",
                    Language.EN to "Fashion and textile products",
                    Language.AR to "منتجات الموضة والمنسوجات"
                ),
                iconName = "clothing",
                brochureCount = 8
            ),
            Category(
                id = 3,
                name = mapOf(
                    Language.TR to "Ev & Yaşam",
                    Language.EN to "Home & Living",
                    Language.AR to "المنزل والمعيشة"
                ),
                description = mapOf(
                    Language.TR to "Ev dekorasyonu ve yaşam ürünleri",
                    Language.EN to "Home decoration and living products",
                    Language.AR to "ديكور المنزل ومنتجات المعيشة"
                ),
                iconName = "home",
                brochureCount = 12
            ),
            Category(
                id = 4,
                name = mapOf(
                    Language.TR to "Spor",
                    Language.EN to "Sports",
                    Language.AR to "رياضة"
                ),
                description = mapOf(
                    Language.TR to "Spor ekipmanları ve aktiviteler",
                    Language.EN to "Sports equipment and activities",
                    Language.AR to "المعدات الرياضية والأنشطة"
                ),
                iconName = "sports",
                brochureCount = 6
            )
        )
    }

    fun getCategory(id: Int): Category? {
        return getCategories().find { it.id == id }
    }
}
