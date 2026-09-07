package com.example.ui.components

import com.example.ui.theme.PurpleAccent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.SuccessGreen
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter
import kotlin.math.roundToInt

@Composable
fun ResultDashboard(result: CalculationResult, scrollOffset: Int = 0, activeStage: Int = 0, isCompact: Boolean = false, onClick: (() -> Unit)? = null) {
    var flipAngle by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(activeStage) {
        val currentStage = (Math.round(flipAngle / 180f) % 4).toInt().let { if (it < 0) it + 4 else it }
        if (currentStage != activeStage) {
            var diff = activeStage - currentStage
            if (diff < 0) diff += 4
            flipAngle += diff * 180f
        }
    }

    val rotation by androidx.compose.animation.core.animateFloatAsState(
        targetValue = flipAngle,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 800, easing = androidx.compose.animation.core.FastOutSlowInEasing),
        label = "flip"
    )

    // Just use activeStage to display the content to avoid infinite recomposition based on rotation
    val currentSide = Math.round(rotation / 180f)
    val displayedStage = (currentSide % 4).let { if (it < 0) it + 4 else it }

    val gradientBrush = Brush.linearGradient(
        colors = listOf(PurpleAccent, Color(0xFF4C1D95)) // Purple gradient matching image
    )
    
    val state = com.example.ui.components.dashboard.getDashboardState(displayedStage, isCompact, result)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isCompact) 150.dp else androidx.compose.ui.unit.Dp.Unspecified)
            .graphicsLayer {
                // 3D Parallax Tilt based on scroll + Flip rotation
                rotationX = -(scrollOffset * 0.015f).coerceIn(-5f, 5f)
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        shape = RoundedCornerShape(32.dp),
        color = Color.Transparent,
        shadowElevation = 16.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBrush)
                .graphicsLayer {
                    rotationY = if (currentSide % 2 != 0) 180f else 0f
                }
        ) {
            com.example.ui.components.dashboard.DecorativeCircles()
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (isCompact) 12.dp else 32.dp)
            ) {
                val diff = state.mainAmount - result.currentBasic
                val percentage = (diff.toDouble() / result.currentBasic * 100).coerceAtLeast(0.0)
                if (isCompact) {
                    com.example.ui.components.dashboard.CompactDashboardContent(state, percentage, displayedStage)
                } else {
                    com.example.ui.components.dashboard.ExpandedDashboardContent(state, percentage, displayedStage)
                }
                
                Spacer(modifier = Modifier.height(if (isCompact) 8.dp else 28.dp))
                HorizontalDivider(color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(if (isCompact) 8.dp else 20.dp))
                
                com.example.ui.components.dashboard.DashboardSubAmounts(state, isCompact, displayedStage)
            }
            
            if (onClick != null) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Next",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(16.dp)
                )
            }
        }
    }
}
