package com.example.kisanmitra.utils

import android.content.Context

object LanguageManager {

    private const val PREF_NAME = "kisanmitra_preferences"
    private const val LANGUAGE_KEY = "language"

    fun saveLanguage(
        context: Context,
        language: String
    ) {
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(LANGUAGE_KEY, language)
            .apply()
    }

    fun getLanguage(
        context: Context
    ): String {
        return context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .getString(LANGUAGE_KEY, "en")
            ?: "en"
    }
}