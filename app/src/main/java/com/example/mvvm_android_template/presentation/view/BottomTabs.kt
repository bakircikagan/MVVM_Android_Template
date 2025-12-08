// presentation/BottomTabs.kt
package com.example.mvvm_android_template.presentation.view

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mvvm_android_template.application.view_model.BottomTabsViewModel
import com.example.mvvm_android_template.domain.BottomTabItem

@Composable
fun BottomTabs(
    navController: NavController,
    currentRoute: String?
) {
    val viewModel: BottomTabsViewModel = hiltViewModel()
    val items: List<BottomTabItem> by viewModel.items.observeAsState(emptyList())

    val isOnDetails = currentRoute?.startsWith("productDetails") == true

    NavigationBar {
        items.forEach { tab ->
            val selected = if (isOnDetails) {
                // 🔹 On product details: no tab selected
                false
            } else {
                when (tab.route) {
                    "welcome" -> currentRoute?.startsWith("welcome") == true
                    "products" -> currentRoute?.startsWith("products") == true
                    else -> false
                }
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
