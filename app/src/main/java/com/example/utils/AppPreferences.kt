package com.example.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}


class AppPreferences(private val context: Context) {
    companion object {
        private val THEME_KEY = stringPreferencesKey("theme_mode")
        private val LANG_KEY = stringPreferencesKey("app_language")
    }

    val themeMode: Flow<ThemeMode> = context.dataStore.data
        .map { preferences ->
            val themeString = preferences[THEME_KEY] ?: ThemeMode.SYSTEM.name
            ThemeMode.valueOf(themeString)
        }

    val language: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LANG_KEY] ?: "en"
        }

    suspend fun saveThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = themeMode.name
        }
    }

    suspend fun saveLanguage(lang: String) {
        context.dataStore.edit { preferences ->
            preferences[LANG_KEY] = lang
        }
    }
}
