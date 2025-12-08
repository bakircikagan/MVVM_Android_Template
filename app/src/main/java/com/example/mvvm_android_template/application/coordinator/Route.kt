package com.example.mvvm_android_template.application.coordinator

sealed interface Route {
    val path: String

    // Static routes (no args)
    data object Welcome : Route { override val path = "welcome" }
    data object Products : Route { override val path = "products" }
    data object Categories : Route { override val path = "categories" }
    data object Brochures : Route { override val path = "brochures" }

    // Static patterns with args
    data object ProductDetails : Route {
        // must match navArgument name
        override val path = "productDetails/{productId}"
    }

    data object BrochureDetails : Route {
        override val path = "brochureDetails/{brochureId}"
    }

    // Runtime routes with concrete args (used by Coordinator/ViewModels)
    data class ProductDetailsNav(val productId: Int) : Route {
        override val path = "productDetails/$productId"
    }

    data class BrochureDetailsNav(val brochureId: Int) : Route {
        override val path = "brochureDetails/$brochureId"
    }
}
