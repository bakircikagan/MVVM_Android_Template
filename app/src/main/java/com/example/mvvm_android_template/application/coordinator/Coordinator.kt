package com.example.mvvm_android_template.application.coordinator

import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

// Navigation commands the app supports
// Bir viewmodel kullanan birkac view

sealed interface NavCommand {
    data class To(val route: Route, val clearStack: Boolean = false) : NavCommand
    object Back : NavCommand
}

// DI-friendly Coordinator
@Singleton
class Coordinator @Inject constructor() {

    private lateinit var navController: NavController
    private val _commands = MutableSharedFlow<NavCommand>()
    val commands: SharedFlow<NavCommand> = _commands

    suspend fun navigate(route: Route, clearStack: Boolean = false) {
        _commands.emit(NavCommand.To(route, clearStack))
    }

    suspend fun navigate(command: NavCommand) {
        _commands.emit(command)
    }

    private fun navigateTo(route: Route, clearStack: Boolean) {
        navController.navigate(route.path) {
            if (clearStack) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
            launchSingleTop = true
        }
    }


    // Called once from the UI after NavController is created
    fun attachNavController(controller: NavController) {
        this.navController = controller
    }

    suspend fun start() {
        commands.collect { command ->
            when (command) {
                is NavCommand.To -> navigateTo(command.route, command.clearStack)
                NavCommand.Back -> navController.popBackStack()
            }

        }
    }
}
