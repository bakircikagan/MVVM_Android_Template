package com.example.mvvm_android_template.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mvvm_android_template.application.view_model.WelcomeViewModel
import com.example.mvvm_android_template.domain.WelcomeTexts
import com.example.mvvm_android_template.domain.language.Language
import com.example.mvvm_android_template.presentation.view.LanguageSwitcher

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel
) {
    val selectedLanguage by viewModel.selectedLanguage.observeAsState(Language.TR)
    val isLtr by viewModel.isLtr.observeAsState(false)
    val texts by viewModel.texts.observeAsState(WelcomeTexts("", ""))

    DirectionalScreen(isLtr = isLtr) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                LanguageSwitcher(
                    selected = selectedLanguage,
                    isLtr = isLtr,
                    onLanguageSelected = { lang -> viewModel.onLanguageSelected(lang) }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = texts.title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = texts.subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFB0B0B0),
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Powered by MVVM-C",
                    color = Color(0xFF666666)
                )
            }
        }
    }
}
