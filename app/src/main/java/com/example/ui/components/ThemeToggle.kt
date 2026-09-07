package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.utils.ThemeMode
import com.example.utils.AppPreferences
import kotlinx.coroutines.launch

import androidx.compose.runtime.collectAsState

@Composable
fun ThemeToggle() {
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val themePreferences = remember { AppPreferences(context) }
    val currentTheme by themePreferences.themeMode.collectAsState(initial = ThemeMode.SYSTEM)

    Box {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .clip(CircleShape)
                .background(androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
        ) {
            Icon(
                imageVector = when (currentTheme) {
                    ThemeMode.LIGHT -> Icons.Default.LightMode
                    ThemeMode.DARK -> Icons.Default.DarkMode
                    ThemeMode.SYSTEM -> Icons.Default.SettingsBrightness
                },
                contentDescription = "Toggle Theme",
                tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("System Default") },
                onClick = {
                    scope.launch { themePreferences.saveThemeMode(ThemeMode.SYSTEM) }
                    expanded = false
                },
                leadingIcon = {
                    Icon(Icons.Default.SettingsBrightness, contentDescription = null)
                }
            )
            DropdownMenuItem(
                text = { Text("Light Mode") },
                onClick = {
                    scope.launch { themePreferences.saveThemeMode(ThemeMode.LIGHT) }
                    expanded = false
                },
                leadingIcon = {
                    Icon(Icons.Default.LightMode, contentDescription = null)
                }
            )
            DropdownMenuItem(
                text = { Text("Dark Mode") },
                onClick = {
                    scope.launch { themePreferences.saveThemeMode(ThemeMode.DARK) }
                    expanded = false
                },
                leadingIcon = {
                    Icon(Icons.Default.DarkMode, contentDescription = null)
                }
            )
        }
    }
}
