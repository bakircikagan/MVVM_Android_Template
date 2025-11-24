package com.example.mvvm_android_template.data

import com.example.mvvm_android_template.domain.model.language.Language
import com.example.mvvm_android_template.domain.model.Product

class FakeProductRepository : ProductRepository {

    override suspend fun getProducts(language: Language): List<Product> {
        return when (language) {

            Language.TR -> listOf(
                Product(
                    id = 1,
                    name = "Laptop",
                    category = "Elektronik",
                    description = "Güçlü işlemcisi ve uzun pil ömrüyle günlük kullanım ve profesyonel işler için ideal bir dizüstü bilgisayar."
                ),
                Product(
                    id = 2,
                    name = "Tişört",
                    category = "Giyim",
                    description = "Yumuşak pamuktan üretilmiş, nefes alabilen rahat günlük kullanım tişörtü."
                ),
                Product(
                    id = 3,
                    name = "Kahve Bardağı",
                    category = "Ev & Mutfak",
                    description = "Sıcak ve soğuk içecekler için uygun, seramik malzemeden şık tasarımlı kahve bardağı."
                ),
                Product(
                    id = 4,
                    name = "Gaming Mouse",
                    category = "Elektronik",
                    description = "Yüksek hassasiyetli sensör, RGB aydınlatma ve ergonomik yapısı ile oyuncular için tasarlanmış fare."
                ),
                Product(
                    id = 5,
                    name = "Kot Pantolon",
                    category = "Giyim",
                    description = "Her tarza uygun, dayanıklı kumaştan üretilmiş rahat kesim kot pantolon."
                ),
                Product(
                    id = 6,
                    name = "Blender",
                    category = "Ev Aletleri",
                    description = "Smoothie, çorba ve sos hazırlamak için ideal çok fonksiyonlu güçlü blender."
                )
            )

            Language.EN -> listOf(
                Product(
                    id = 1,
                    name = "Laptop",
                    category = "Electronics",
                    description = "A high-performance laptop with a fast processor and long battery life, perfect for work, study, and entertainment."
                ),
                Product(
                    id = 2,
                    name = "T-Shirt",
                    category = "Clothing",
                    description = "A soft, breathable cotton t-shirt designed for everyday comfort and style."
                ),
                Product(
                    id = 3,
                    name = "Coffee Mug",
                    category = "Home & Kitchen",
                    description = "A stylish ceramic coffee mug suitable for both hot and cold beverages."
                ),
                Product(
                    id = 4,
                    name = "Gaming Mouse",
                    category = "Electronics",
                    description = "Ergonomic gaming mouse with RGB lighting and a high-precision sensor for competitive gameplay."
                ),
                Product(
                    id = 5,
                    name = "Jeans",
                    category = "Clothing",
                    description = "Durable and comfortable denim jeans that match any casual outfit."
                ),
                Product(
                    id = 6,
                    name = "Blender",
                    category = "Appliances",
                    description = "A powerful multi-purpose blender ideal for making smoothies, soups, and sauces."
                )
            )

            Language.AR -> listOf(
                Product(
                    id = 1,
                    name = "حاسوب محمول",
                    category = "إلكترونيات",
                    description = "حاسوب محمول عالي الأداء مع بطارية طويلة الأمد، مناسب للعمل والدراسة والترفيه."
                ),
                Product(
                    id = 2,
                    name = "قميص قطني",
                    category = "ملابس",
                    description = "قميص قطني ناعم يسمح بمرور الهواء ومناسب للاستخدام اليومي."
                ),
                Product(
                    id = 3,
                    name = "كوب قهوة",
                    category = "المنزل والمطبخ",
                    description = "كوب قهوة أنيق مصنوع من السيراميك مناسب للمشروبات الساخنة والباردة."
                ),
                Product(
                    id = 4,
                    name = "فأرة ألعاب",
                    category = "إلكترونيات",
                    description = "فأرة ألعاب مريحة مع إضاءة RGB ومستشعر عالي الدقة مخصصة للاعبين."
                ),
                Product(
                    id = 5,
                    name = "بنطال جينز",
                    category = "ملابس",
                    description = "بنطال جينز متين ومريح يناسب الإطلالات اليومية."
                ),
                Product(
                    id = 6,
                    name = "خلاط كهربائي",
                    category = "أجهزة منزلية",
                    description = "خلاط قوي متعدد الاستخدامات مثالي لتحضير العصائر والشوربات والصلصات."
                )
            )
        }
    }
}
