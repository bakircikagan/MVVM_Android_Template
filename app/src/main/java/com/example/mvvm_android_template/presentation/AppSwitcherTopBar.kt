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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mvvm_android_template.application.view_model.ActiveApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSwitcherTopBar(
    activeApp: ActiveApp,
    onAppSelected: (ActiveApp) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = when (activeApp) {
                    ActiveApp.E_COMMERCE -> "E-Commerce"
                    ActiveApp.BROCHURES -> "Brochures"
                }
            )
        },
        actions = {
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
                DropdownMenuItem(
                    text = { Text("E-Commerce") },
                    onClick = {
                        expanded = false
                        onAppSelected(ActiveApp.E_COMMERCE)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Brochures") },
                    onClick = {
                        expanded = false
                        onAppSelected(ActiveApp.BROCHURES)
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors()
    )
}
