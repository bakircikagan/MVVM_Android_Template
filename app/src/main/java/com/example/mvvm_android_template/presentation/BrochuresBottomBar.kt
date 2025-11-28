package com.example.mvvm_android_template.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BrochuresBottomBar(
    navController: NavController,
    currentRoute: String?
) {
    val selected = currentRoute?.startsWith("brochures") == true

    NavigationBar {
        NavigationBarItem(
            selected = selected,
            onClick = {
                navController.navigate("brochures") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Apps, // you can change to MenuBook
                    contentDescription = "Brochures"
                )
            },
            label = { Text("Brochures") }
        )
    }
}
