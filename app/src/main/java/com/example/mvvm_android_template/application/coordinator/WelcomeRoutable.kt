package com.example.mvvm_android_template.application.coordinator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvm_android_template.application.view_model.WelcomeViewModel
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.presentation.screen.WelcomeScreen
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WelcomeRoutable @Inject constructor() : Routable {

    override val routeConfig = RouteConfig(
        route = Route.Welcome,
        app = ActiveApp.E_COMMERCE,
        tab = TabConfig(
            identifier = "welcome",
            icon = Icons.Filled.Home,
            index = 0,
            getLabel = { language ->
                when (language) {
                    Language.TR -> "Giriş"
                    Language.EN -> "Welcome"
                    Language.AR -> "الرئيسية"
                }
            }
        )
    )

    override fun register(builder: NavGraphBuilder) {
        builder.composable(routeConfig.route.path) {
            val vm: WelcomeViewModel = hiltViewModel()
            WelcomeScreen(viewModel = vm)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindingModule {
        @Binds
        @IntoSet
        fun bind(impl: WelcomeRoutable): Routable
    }
}
