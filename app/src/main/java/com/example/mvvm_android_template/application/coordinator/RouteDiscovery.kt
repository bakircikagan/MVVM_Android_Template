package com.example.mvvm_android_template.application.coordinator

import com.example.mvvm_android_template.application.view_model.ActiveApp
import com.example.mvvm_android_template.domain.BottomTabItem
import com.example.mvvm_android_template.domain.language.Language
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Automatically discovers and organizes routes by activity and tabs.
 * This eliminates the need to manually manage activities and tabs -
 * they are auto-discovered from Routable implementations.
 */
@Singleton
class RouteDiscovery @Inject constructor(
    private val routes: Set<@JvmSuppressWildcards Routable>
) {

    /**
     * Get all routes for a specific activity
     */
    fun getRoutesForActivity(activity: ActiveApp): List<Routable> {
        return routes.filter { it.routeConfig.activity == activity }
    }

    /**
     * Get all tabs for a specific activity, sorted by index
     */
    fun getTabsForActivity(activity: ActiveApp, language: Language): List<BottomTabItem> {
        return routes
            .filter { it.routeConfig.activity == activity && it.routeConfig.tab != null }
            .sortedBy { it.routeConfig.tab!!.index }
            .map { routable ->
                val tab = routable.routeConfig.tab!!
                BottomTabItem(
                    route = routable.routeConfig.path,
                    label = tab.getLabel(language),
                    icon = tab.icon
                )
            }
    }

    /**
     * Get all discovered activities
     */
    fun getActivities(): List<ActiveApp> {
        return routes.map { it.routeConfig.activity }.distinct()
    }

    /**
     * Get the start destination for an activity
     * (the first tab route, or first route if no tabs)
     */
    fun getStartDestination(activity: ActiveApp): String? {
        val activityRoutes = getRoutesForActivity(activity)

        // Prefer the first tab route
        val firstTab = activityRoutes
            .filter { it.routeConfig.tab != null }
            .minByOrNull { it.routeConfig.tab!!.index }

        return firstTab?.routeConfig?.path
            ?: activityRoutes.firstOrNull()?.routeConfig?.path
    }

    /**
     * Check if an activity has tabs
     */
    fun hasTabsForActivity(activity: ActiveApp): Boolean {
        return routes.any { it.routeConfig.activity == activity && it.routeConfig.tab != null }
    }

    /**
     * Get NavCommand for a tab route
     */
    fun getNavCommandForRoute(route: String): NavCommand? {
        return when (route) {
            "welcome" -> NavCommand.ToWelcome
            "products" -> NavCommand.ToProducts
            "brochures" -> NavCommand.ToBrochures
            "categories" -> NavCommand.ToCategories
            else -> {
                // Handle dynamic routes with parameters
                when {
                    route.startsWith("productDetails/") -> {
                        val id = route.substringAfter("productDetails/").toIntOrNull()
                        id?.let { NavCommand.ToProductDetails(it) }
                    }
                    route.startsWith("brochureDetails/") -> {
                        val id = route.substringAfter("brochureDetails/").toIntOrNull()
                        id?.let { NavCommand.ToBrochureDetails(it) }
                    }
                    else -> null
                }
            }
        }
    }
}
