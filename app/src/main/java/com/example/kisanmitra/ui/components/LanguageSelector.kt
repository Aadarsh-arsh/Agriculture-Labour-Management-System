package com.example.kisanmitra.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LanguageSelector(
    isHindi: Boolean,
    onLanguageChange: (Boolean) -> Unit
) {
    Button(
        onClick = {
            onLanguageChange(!isHindi)
        }
    ) {
        Text(
            text = if (isHindi) {
                "English"
            } else {
                "हिंदी"
            }
        )
    }
}