package com.example.mvvm_android_template.presentation.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.mvvm_android_template.application.view_model.BrochuresViewModel
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.domain.language.isLtrLanguage
import com.example.mvvm_android_template.presentation.view.BrochureCard

@Composable
fun BrochuresScreen(vm: BrochuresViewModel) {
    val brochures by vm.brochures.observeAsState(emptyList())
    val language by vm.language.observeAsState(Language.TR)
    val isLtr = isLtrLanguage(language)
    DirectionalScreen(isLtr) {
        LazyColumn {
            items(brochures) { brochure ->
                BrochureCard(
                    brochure = brochure,
                    language = language,
                    onClick = { vm.onBrochureSelected(brochure.id) }
                )
            }
        }
    }
}
