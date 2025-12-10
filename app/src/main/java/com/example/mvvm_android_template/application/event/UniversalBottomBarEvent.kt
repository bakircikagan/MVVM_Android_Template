package com.example.mvvm_android_template.application.event

import com.example.mvvm_android_template.application.coordinator.ActiveApp

sealed interface UniversalBottomBarEvent: Event {
    data class TabSelected(val route: String): UniversalBottomBarEvent
    data class AppChanged(val app: ActiveApp): UniversalBottomBarEvent
}