package com.example.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfile
import com.example.data.UserProfileManager
import com.example.utils.AppPreferences
import com.example.utils.ThemeMode
import kotlinx.coroutines.launch
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.DarkCard

@Composable
fun SidebarSettingsCard(
    profile: UserProfile,
    userProfileManager: UserProfileManager
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Card(
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Preferences",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            

            
            // Language Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Language,
                        contentDescription = "Language",
                        tint = AccentPurpleLight,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("বাংলা", color = Color.White, fontSize = 14.sp)
                }
                Switch(
                    checked = profile.appLanguage == "bn",
                    onCheckedChange = { isBangla ->
                        val newLang = if (isBangla) "bn" else "en"
                        userProfileManager.saveProfile(profile.copy(appLanguage = newLang))
                        (context as? Activity)?.recreate()
                    },
                    colors = SwitchDefaults.colors(checkedThumbColor = AccentPurpleLight, checkedTrackColor = AccentPurpleLight.copy(alpha = 0.5f))
                )
            }
        }
    }
}
