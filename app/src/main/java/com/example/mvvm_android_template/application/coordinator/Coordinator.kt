package com.example.mvvm_android_template.application.coordinator

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

// Navigation commands the app supports
// TODO: URL den cevir
sealed interface NavCommand {
    data object ToWelcome : NavCommand
    data object ToProducts : NavCommand
    data class ToProductDetails(val productId: Int) : NavCommand

    object ToBrochures : NavCommand
    data class ToBrochureDetails(val brochureId: Int) : NavCommand

    data object Back : NavCommand
}

// DI-friendly Coordinator
@Singleton
class Coordinator @Inject constructor() {
    private val _commands = MutableSharedFlow<NavCommand>()
    val commands: SharedFlow<NavCommand> = _commands

    suspend fun navigate(command: NavCommand) {
        _commands.emit(command)
    }
}
