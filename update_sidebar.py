with open('app/src/main/java/com/example/ui/components/DeveloperSidebar.kt', 'r') as f:
    content = f.read()

import re

new_content = """package com.example.ui.components

import android.app.Activity
import com.example.ui.theme.DeepDarkCard
import com.example.ui.theme.DarkBorder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfileManager
import com.example.utils.AppPreferences
import com.example.utils.ThemeMode
import kotlinx.coroutines.launch
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.AccentPurpleLight

@Composable
fun DeveloperSidebar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val appPreferences = remember { AppPreferences(context) }
    val themeMode by appPreferences.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
    val userProfileManager = remember { UserProfileManager.getInstance(context) }
    val profile by userProfileManager.profile.collectAsState()

    ModalDrawerSheet(
        modifier = modifier.width(300.dp),
        drawerContainerColor = DeepDarkCard,
        drawerContentColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Developer Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(DarkBorder),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = com.example.R.drawable.developer_photo),
                    contentDescription = "Developer Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Name
            Text(
                text = "BIDDUTH",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Developer",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Settings Toggles
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Theme Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (themeMode == ThemeMode.LIGHT) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
                                contentDescription = "Theme",
                                tint = AccentPurpleLight,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Dark Theme", color = Color.White, fontSize = 14.sp)
                        }
                        Switch(
                            checked = themeMode != ThemeMode.LIGHT,
                            onCheckedChange = { isDark ->
                                scope.launch {
                                    appPreferences.saveThemeMode(if (isDark) ThemeMode.DARK else ThemeMode.LIGHT)
                                }
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = AccentPurpleLight, checkedTrackColor = AccentPurpleLight.copy(alpha = 0.5f))
                        )
                    }
                    
                    Divider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 8.dp))
                    
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
                                // Recreate activity to apply new locale
                                (context as? Activity)?.recreate()
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = AccentPurpleLight, checkedTrackColor = AccentPurpleLight.copy(alpha = 0.5f))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            // Contact Info
            ContactRow(
                icon = Icons.Default.Email,
                text = "ctrkb79@gmail.com"
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ContactRow(
                icon = Icons.Default.Phone,
                text = "01829300000"
            )
        }
    }
}

@Composable
fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BluePrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp
        )
    }
}
"""

with open('app/src/main/java/com/example/ui/components/DeveloperSidebar.kt', 'w') as f:
    f.write(new_content)
