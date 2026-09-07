package com.example.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import com.example.ui.theme.LocalDarkTheme

val BluePrimary = Color(0xFF1D4ED8) // Blue 700
val BluePrimaryDark = Color(0xFF1E3A8A) // Blue 900
val BlueLight = Color(0xFFEFF6FF) // Blue 50
val SkyBlueSecondary = Color(0xFF38BDF8)
val BackgroundLight = Color(0xFFF8FAFC)
val SurfaceWhite = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF0F172A)
val TextSecondary = Color(0xFF64748B)

val SuccessGreen = Color(0xFF10B981)
val SuccessGreenBg = Color(0xFFD1FAE5)
val WarningAmber = Color(0xFFF59E0B)
val ErrorRed = Color(0xFFEF4444)

// Required baseline colors
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Premium Dark Theme Colors
val DarkBackground: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF120F16) else BackgroundLight
val PureDark: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF0F0F11) else Color(0xFFF1F5F9)
val DarkCard: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF1C1924) else Color.White
val DarkCardInner: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF262130) else Color(0xFFF8FAFC)
val DarkCardAlternative: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF252131) else Color(0xFFF1F5F9)
val DarkDialogBg: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xE01C1924) else Color(0xE0FFFFFF)
val DarkBorder: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF3B3247) else Color(0xFFE2E8F0)
val ChipBg: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF2C2C2E) else Color(0xFFF1F5F9)
val SecondaryBarBg: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF161619) else Color(0xFFF8FAFC)
val TextGray: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF9CA3AF) else Color(0xFF64748B)
val TextPrimaryDark: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFFFFFFFF) else TextPrimary
val TextSecondaryDark: Color
    @Composable get() = if (LocalDarkTheme.current) Color.Gray else TextSecondary

// Theme Accents
val AccentPurple = Color(0xFF9D4EDD)
val AccentPurpleLight = Color(0xFFC77DFF)
val RedAccent = Color(0xFFF83648)
val PurpleAccent = Color(0xFF8B5CF6)
val GrayLight = Color(0xFFE5E7EB)
val LightRed = Color(0xFFFF6B6B)
val DarkSlate = Color(0xFF37474F)
val BlueAccent = Color(0xFF3B82F6)
val DeepDarkCard: Color
    @Composable get() = if (LocalDarkTheme.current) Color(0xFF1C1326) else Color.White

// Chart Colors
val ChartRed = Color(0xFFFF0000)
val ChartGreen = Color(0xFF4ADE80)
val ChartOrange = Color(0xFFFBBF24)
val ChartPurple = Color(0xFFC084FC)
