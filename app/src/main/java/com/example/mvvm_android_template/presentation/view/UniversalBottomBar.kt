package com.example.mvvm_android_template.presentation.view

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mvvm_android_template.application.coordinator.ActiveApp
import com.example.mvvm_android_template.application.view_model.UniversalTabsViewModel
import com.example.mvvm_android_template.domain.BottomTabItem

/**
 * Universal bottom navigation bar that automatically displays tabs
 * for the current activity. No manual tab management needed.
 */
@Composable
fun UniversalBottomBar(
    navController: NavController,
    currentRoute: String?,
    app: ActiveApp,
    viewModel: UniversalTabsViewModel = hiltViewModel()
) {
    // Update the activity when it changes
    LaunchedEffect(app) {
        viewModel.setActivity(app)
    }

    val items: List<BottomTabItem> by viewModel.items.observeAsState(emptyList())

    // Don't show bottom bar if there are no tabs
    if (items.isEmpty()) return

    val isOnDetails = currentRoute?.contains("Details") == true

    NavigationBar {
        items.forEach { tab ->
            val selected = if (isOnDetails) {
                // On detail screens: no tab selected
                false
            } else {
                currentRoute?.startsWith(tab.route) == true
            }

            NavigationBarItem(
                selected = selected,
                onClick = { viewModel.onTabSelected(tab.route) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label
                    )
                },
                label = { Text(tab.label) }
            )
        }
    }
}
