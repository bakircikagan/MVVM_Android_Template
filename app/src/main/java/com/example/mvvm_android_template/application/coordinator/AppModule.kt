package com.example.mvvm_android_template.application.coordinator

import com.example.mvvm_android_template.domain.language.InMemoryLanguageSubject
import com.example.mvvm_android_template.domain.language.LanguageSubject
import com.example.mvvm_android_template.infrastructure.FakeProductRepository
import com.example.mvvm_android_template.infrastructure.WelcomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideProductRepository(): FakeProductRepository = FakeProductRepository()

    @Provides
    @Singleton
    fun provideLanguageSubject(): LanguageSubject = InMemoryLanguageSubject()

    @Provides
    @Singleton
    fun provideWelcomeRepository(): WelcomeRepository = WelcomeRepository()
}
