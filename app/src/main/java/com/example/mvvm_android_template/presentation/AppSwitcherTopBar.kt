package com.example.mvvm_android_template.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvm_android_template.application.view_model.ActiveApp
import com.example.mvvm_android_template.application.view_model.AppSwitcherViewModel
import com.example.mvvm_android_template.domain.language.Language

/**
 * Dynamic app switcher that automatically discovers and displays all available apps.
 * No need to manually add apps - they're discovered from routes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSwitcherTopBar(
    activeApp: ActiveApp,
    onAppSelected: (ActiveApp) -> Unit,
    viewModel: AppSwitcherViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }

    val availableApps by viewModel.availableApps.observeAsState(emptyList())
    val language by viewModel.language.observeAsState(Language.TR)

    CenterAlignedTopAppBar(
        title = {
            Text(text = activeApp.getLabel(language))
        },
        actions = {
            // Only show switcher if there are multiple apps
            if (availableApps.size > 1) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.Apps,
                        contentDescription = "Switch app"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    availableApps.forEach { app ->
                        DropdownMenuItem(
                            text = { Text(app.getLabel(language)) },
                            onClick = {
                                expanded = false
                                onAppSelected(app)
                            }
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors()
    )
}
