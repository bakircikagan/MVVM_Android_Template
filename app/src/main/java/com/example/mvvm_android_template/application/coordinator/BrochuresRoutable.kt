package com.example.mvvm_android_template.application.coordinator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mvvm_android_template.application.view_model.BrochuresViewModel
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.presentation.screen.BrochuresScreen
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrochuresRoutable @Inject constructor() : Routable {

    override val routeConfig = RouteConfig(
        route = Route.Brochures,
        app = ActiveApp.BROCHURES,
        tab = TabConfig(
            identifier = "brochures",
            icon = Icons.Filled.Apps,
            index = 0,
            getLabel = { language ->
                when (language) {
                    Language.TR -> "Broşürler"
                    Language.EN -> "Brochures"
                    Language.AR -> "كتيبات"
                }
            }
        )
    )

    override fun register(builder: NavGraphBuilder) {
        builder.composable(routeConfig.route.path) {
            val vm: BrochuresViewModel = hiltViewModel()
            BrochuresScreen(vm)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindingModule {
        @Binds
        @IntoSet
        fun bind(impl: BrochuresRoutable): Routable
    }
}
