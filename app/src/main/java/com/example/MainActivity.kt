package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import kotlin.math.roundToInt

import com.example.data.PayScale2015
import com.example.ui.components.*
import com.example.ui.theme.AppTheme
import com.example.ui.theme.BackgroundLight
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter
import com.example.utils.SalaryCalculator
import kotlinx.coroutines.launch

import com.example.utils.AppPreferences
import com.example.utils.ThemeMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: android.content.Context) {
        val prefs = newBase.getSharedPreferences("user_profile", android.content.Context.MODE_PRIVATE)
        val lang = prefs.getString("app_language", "en") ?: "en"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = android.content.res.Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val darkTheme = true

            val userProfileManager = remember { com.example.data.UserProfileManager.getInstance(context) }
            val profile by userProfileManager.profile.collectAsState()
            
            AppTheme(darkTheme = darkTheme) {
                val currentDensity = LocalDensity.current
                val customDensity = if (profile.appLanguage == "bn") {
                    androidx.compose.ui.unit.Density(currentDensity.density, currentDensity.fontScale * 0.9f)
                } else currentDensity
                
                CompositionLocalProvider(LocalDensity provides customDensity) {
                    TouchSweepOverlay {
                        com.example.ui.screens.HomeScreen()
                    }
                }
            }
        }
    }
}
