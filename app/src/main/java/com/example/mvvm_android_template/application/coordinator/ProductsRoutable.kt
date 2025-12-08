package com.example.mvvm_android_template.application.coordinator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mvvm_android_template.application.view_model.ProductsViewModel
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.presentation.screen.ProductsScreen
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRoutable @Inject constructor() : Routable {

    override val routeConfig = RouteConfig(
        route = Route.Products,
        app = ActiveApp.E_COMMERCE,
        tab = TabConfig(
            identifier = "products",
            icon = Icons.Filled.List,
            index = 1,
            getLabel = { language ->
                when (language) {
                    Language.TR -> "Ürünler"
                    Language.EN -> "Products"
                    Language.AR -> "المنتجات"
                }
            }
        )
    )

    override fun register(builder: NavGraphBuilder) {
        builder.composable(routeConfig.route.path) {
            val viewModel: ProductsViewModel = hiltViewModel()
            ProductsScreen(viewModel = viewModel)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindingModule {
        @Binds
        @IntoSet
        fun bind(impl: ProductsRoutable): Routable
    }
}
