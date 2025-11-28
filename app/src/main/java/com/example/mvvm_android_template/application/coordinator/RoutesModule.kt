package com.example.mvvm_android_template.application.coordinator

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class RoutesModule {

    @Binds
    @IntoSet
    abstract fun bindProductsRoutable(
        impl: ProductsRoutable
    ): Routable

    @Binds
    @IntoSet
    abstract fun bindProductDetailsRoutable(
        impl: ProductDetailsRoutable
    ): Routable

    @Binds
    @IntoSet
    abstract fun bindWelcomeRoutable(
        impl: WelcomeRoutable
    ): Routable

    @Binds
    @IntoSet
    abstract fun bindBrochuresRoutable(
        impl: BrochuresRoutable
    ): Routable

}
