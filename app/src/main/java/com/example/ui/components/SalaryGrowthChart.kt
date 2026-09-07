package com.example.ui.components

import com.example.ui.theme.SuccessGreen

import com.example.ui.theme.AccentPurpleLight

import com.example.ui.theme.AccentPurple

import com.example.ui.theme.DarkBorder

import com.example.ui.theme.DarkCard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter
import com.example.ui.components.chart.*
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import com.example.data.UserProfileManager
import androidx.compose.foundation.layout.Arrangement


@Composable
fun SalaryGrowthChart(result: CalculationResult, scrollOffset: Int = 0, isCompact: Boolean = false) {
    var startAnimation by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    
    // Flip state
    val context = LocalContext.current
    val profile by remember { UserProfileManager.getInstance(context).profile }.collectAsState()
    
    var targetFlipCount by remember { mutableIntStateOf(0) }
    val totalStates = 7
    
    LaunchedEffect(result) {
        startAnimation = false
        selectedIndex = -1
        delay(100)
        startAnimation = true
    }

    val rotation by animateFloatAsState(
        targetValue = targetFlipCount * 180f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "flip"
    )
    
    val currentSide = Math.round(rotation / 180f)
    val displayIndex = (currentSide % totalStates).let { if (it < 0) it + totalStates else it }
    val contentRotation = if (currentSide % 2 != 0) 180f else 0f
    
    // Memoized Calculations
    val calcData = remember(result, profile) {
        com.example.utils.calculateSalaryBreakdown(result, profile)
    }

    val maxSalary = result.stage3.toFloat().coerceAtLeast(1f) // Avoid division by zero
    
    val bars = listOf(
        ChartBarData("Current", result.currentBasic, Color(0xFF6B7280)),
        ChartBarData("Stage-1", result.stage1, AccentPurpleLight), // Light purple
        ChartBarData("Stage-2", result.stage2, AccentPurple), // Violet
        ChartBarData("Stage-3", result.stage3, SuccessGreen)  // Green
    )

    // Animation progress for front side bars
    val frontAnimationProgress = remember { Animatable(0f) }

    LaunchedEffect(result, displayIndex) {
        if (displayIndex == 0) {
            frontAnimationProgress.snapTo(0f)
            frontAnimationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isCompact) 150.dp else 280.dp)
            .graphicsLayer {
                // 3D Parallax Tilt based on scroll + Flip rotation
                rotationX = -(scrollOffset * 0.01f).coerceIn(-3f, 3f)
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                targetFlipCount++
            },
        shape = RoundedCornerShape(24.dp),
        color = DarkCard.copy(alpha = 0.8f), // Translucent dark background
        shadowElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder.copy(alpha = 0.5f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isCompact) 12.dp else 20.dp)
                .graphicsLayer {
                    rotationY = contentRotation
                }
        ) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = "Next",
                tint = Color.White.copy(alpha = 0.2f),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(16.dp)
            )

            ChartFlipContent(
                displayIndex = displayIndex,
                isCompact = isCompact,
                bars = bars,
                selectedIndex = selectedIndex,
                maxSalary = maxSalary,
                frontAnimationProgress = frontAnimationProgress.value,
                result = result,
                calcData = calcData
            )
        }
    }
}
