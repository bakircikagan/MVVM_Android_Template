package com.example.mvvm_android_template.application.coordinator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mvvm_android_template.application.view_model.CategoriesViewModel
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.presentation.screen.CategoriesScreen
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRoutable @Inject constructor() : Routable {

    override val routeConfig = RouteConfig(
        route = Route.Categories,
        app = ActiveApp.BROCHURES,
        tab = TabConfig(
            identifier = "categories",
            icon = Icons.Filled.Category,
            index = 1,  // Second tab in BROCHURES app
            getLabel = { language ->
                when (language) {
                    Language.TR -> "Kategoriler"
                    Language.EN -> "Categories"
                    Language.AR -> "الفئات"
                }
            }
        )
    )

    override fun register(builder: NavGraphBuilder) {
        builder.composable(routeConfig.route.path) {
            val vm: CategoriesViewModel = hiltViewModel()
            CategoriesScreen(viewModel = vm)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindingModule {
        @Binds
        @IntoSet
        fun bind(impl: CategoriesRoutable): Routable
    }
}
