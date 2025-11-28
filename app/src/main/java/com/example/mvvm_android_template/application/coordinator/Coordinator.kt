package com.example.mvvm_android_template.application.coordinator

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

// Navigation commands your app supports
sealed interface NavCommand {
    data class ToProductDetails(val productId: Int) : NavCommand
    data object Back : NavCommand
}

// This is effectively your "Coordinator", but DI-friendly
@Singleton
class NavigationManager @Inject constructor() {
    private val _commands = MutableSharedFlow<NavCommand>()
    val commands: SharedFlow<NavCommand> = _commands

    suspend fun navigate(command: NavCommand) {
        _commands.emit(command)
    }
}
