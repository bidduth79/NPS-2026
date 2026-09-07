with open('app/src/main/java/com/example/utils/AppPreferences.kt', 'r') as f:
    content = f.read()

header = """package com.example.utils

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
"""
content = content.replace('package com.example.utils\n\nimport android.content.Context\nimport androidx.datastore.preferences.core.edit\nimport androidx.datastore.preferences.core.stringPreferencesKey\nimport kotlinx.coroutines.flow.Flow\nimport kotlinx.coroutines.flow.map', header)

with open('app/src/main/java/com/example/utils/AppPreferences.kt', 'w') as f:
    f.write(content)

