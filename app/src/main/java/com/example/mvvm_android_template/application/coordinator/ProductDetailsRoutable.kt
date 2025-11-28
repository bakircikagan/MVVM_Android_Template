package com.example.mvvm_android_template.application.coordinator

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mvvm_android_template.application.view_model.ActiveApp
import com.example.mvvm_android_template.application.view_model.ProductDetailsViewModel
import com.example.mvvm_android_template.presentation.ProductDetailsScreen
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDetailsRoutable @Inject constructor() : Routable {

    override val routeConfig = RouteConfig(
        path = "productDetails/{productId}",
        activity = ActiveApp.E_COMMERCE,
        tab = null // Detail screen - no tab
    )

    override fun register(builder: NavGraphBuilder) {
        builder.composable(
            route = routeConfig.path,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            val viewModel: ProductDetailsViewModel = hiltViewModel()
            ProductDetailsScreen(viewModel = viewModel)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindingModule {
        @Binds
        @IntoSet
        fun bind(impl: ProductDetailsRoutable): Routable
    }
}
