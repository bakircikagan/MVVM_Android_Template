package com.example.mvvm_android_template.application.coordinator

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mvvm_android_template.domain.language.Language

/**
 * Configuration for a tab in the bottom navigation bar
 */
data class TabConfig(
    val identifier: String,
    val icon: ImageVector,
    val index: Int,
    val getLabel: (Language) -> String
)

/**
 * Complete route configuration that defines:
 * - Which app this route belongs to
 * - Navigation path
 * - Optional tab configuration for bottom navigation
 */
data class RouteConfig(
    val route: Route,
    val app: ActiveApp,
    val tab: TabConfig? = null
)
