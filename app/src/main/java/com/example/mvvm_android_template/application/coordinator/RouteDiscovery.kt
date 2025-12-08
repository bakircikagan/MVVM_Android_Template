package com.example.mvvm_android_template.application.coordinator

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
     * Get all routes for a specific app
     */
    fun getRoutesForApp(app: ActiveApp): List<Routable> {
        return routes.filter { it.routeConfig.app == app }
    }

    /**
     * Get all tabs for a specific app, sorted by index
     */
    fun getTabsForApp(app: ActiveApp, language: Language): List<BottomTabItem> {
        return routes
            .filter { it.routeConfig.app == app && it.routeConfig.tab != null }
            .sortedBy { it.routeConfig.tab!!.index }
            .map { routable ->
                val tab = routable.routeConfig.tab!!
                BottomTabItem(
                    route = routable.routeConfig.route.path,
                    label = tab.getLabel(language),
                    icon = tab.icon
                )
            }
    }

    /**
     * Get all discovered apps
     */
    fun getApps(): List<ActiveApp> {
        return routes.map { it.routeConfig.app }.distinct()
    }

    /**
     * Get the start destination for an app
     * (the first tab route, or first route if no tabs)
     */
    fun getStartDestination(app: ActiveApp): Route? {
        val appRoutes = getRoutesForApp(app)

        val firstTab = appRoutes
            .filter { it.routeConfig.tab != null }
            .minByOrNull { it.routeConfig.tab!!.index }

        return firstTab?.routeConfig?.route
            ?: appRoutes.firstOrNull()?.routeConfig?.route
    }
}
