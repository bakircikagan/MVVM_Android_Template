package com.example.mvvm_android_template.infrastructure

import com.example.mvvm_android_template.domain.Brochure
import com.example.mvvm_android_template.domain.language.Language
import java.util.Date

class FakeBrochureRepository {

    fun getBrochures(language: Language): List<Brochure> {
        return brochures.map {
            it.copy(
                title = mapOf(language to it.title[language]!!),
                description = mapOf(language to it.description[language]!!),
                category = mapOf(language to it.category[language]!!)
            )
        }
    }

    companion object {
        val brochures = listOf(
            Brochure(
                id = 1,
                title = mapOf(
                    Language.TR to "2024 İlkbahar Koleksiyonu",
                    Language.EN to "Spring Collection 2024",
                    Language.AR to "مجموعة ربيع 2024"
                ),
                description = mapOf(
                    Language.TR to "Taze tasarımlar ve canlı renklerle yeni sezon koleksiyonunu keşfedin.",
                    Language.EN to "Explore our latest spring collection featuring fresh designs and vibrant colors.",
                    Language.AR to "استكشف أحدث مجموعة ربيعية بتصاميم جديدة وألوان زاهية."
                ),
                category = mapOf(
                    Language.TR to "Sezonluk",
                    Language.EN to "Seasonal",
                    Language.AR to "موسمي"
                ),
                imageUrl = "https://images.pexels.com/photos/2983464/pexels-photo-2983464.jpeg",
                pdfUrl = "https://example.com/spring-2024.pdf",
                publishedDate = Date(System.currentTimeMillis() - 86400L * 30 * 1000)
            ),

            Brochure(
                id = 2,
                title = mapOf(
                    Language.TR to "Yaz İndirimi",
                    Language.EN to "Summer Sale",
                    Language.AR to "تخفيضات الصيف"
                ),
                description = mapOf(
                    Language.TR to "Tüm yaz ürünlerinde harika indirimler! Bu fırsatları kaçırmayın.",
                    Language.EN to "Amazing discounts on all summer items. Don't miss out on these incredible deals!",
                    Language.AR to "خصومات رائعة على جميع منتجات الصيف. لا تفوت هذه العروض المذهلة!"
                ),
                category = mapOf(
                    Language.TR to "Kampanya",
                    Language.EN to "Promotional",
                    Language.AR to "ترويجي"
                ),
                imageUrl = "https://images.pexels.com/photos/1124465/pexels-photo-1124465.jpeg",
                pdfUrl = "https://example.com/summer-sale.pdf",
                publishedDate = Date(System.currentTimeMillis() - 86400L * 15 * 1000)
            ),

            Brochure(
                id = 3,
                title = mapOf(
                    Language.TR to "Yeni Gelenler",
                    Language.EN to "New Arrivals",
                    Language.AR to "وصل حديثاً"
                ),
                description = mapOf(
                    Language.TR to "Mağazamıza yeni eklenen ürünleri keşfedin.",
                    Language.EN to "Check out the newest products that just landed in our store.",
                    Language.AR to "اكتشف أحدث المنتجات التي وصلت إلى متجرنا."
                ),
                category = mapOf(
                    Language.TR to "Yeni",
                    Language.EN to "New",
                    Language.AR to "جديد"
                ),
                imageUrl = "https://images.pexels.com/photos/794062/pexels-photo-794062.jpeg",
                pdfUrl = "https://example.com/new-arrivals.pdf",
                publishedDate = Date(System.currentTimeMillis() - 86400L * 7 * 1000)
            ),

            Brochure(
                id = 4,
                title = mapOf(
                    Language.TR to "Yılbaşı Kataloğu",
                    Language.EN to "Holiday Catalog",
                    Language.AR to "كتالوج العطلات"
                ),
                description = mapOf(
                    Language.TR to "Özel hediye önerileriyle dolu yılbaşı alışveriş rehberiniz.",
                    Language.EN to "Your complete guide to holiday shopping with exclusive gift ideas.",
                    Language.AR to "دليل كامل للتسوق في العطلات مع أفكار هدايا مميزة."
                ),
                category = mapOf(
                    Language.TR to "Sezonluk",
                    Language.EN to "Seasonal",
                    Language.AR to "موسمي"
                ),
                imageUrl = "https://images.pexels.com/photos/1303081/pexels-photo-1303081.jpeg",
                pdfUrl = "https://example.com/holiday-catalog.pdf",
                publishedDate = Date(System.currentTimeMillis() - 86400L * 3 * 1000)
            )
        )
    }


}
