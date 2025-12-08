package com.example.mvvm_android_template.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.platform.LocalLayoutDirection

@Composable
fun DirectionalScreen(
    isLtr: Boolean,
    content: @Composable () -> Unit
) {
    val direction = if (isLtr) LayoutDirection.Ltr else LayoutDirection.Rtl
    CompositionLocalProvider(LocalLayoutDirection provides direction) {
        content()
    }
}
