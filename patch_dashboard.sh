cat << 'INNER_EOF' > app/src/main/java/com/example/ui/components/ResultDashboard.kt
package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
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
fun ResultDashboard(result: CalculationResult, scrollOffset: Int = 0, activeStage: Int = 0, onClick: (() -> Unit)? = null) {
    var flipAngle by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(activeStage) {
        val currentStage = (Math.round(flipAngle / 180f) % 4).let { if (it < 0) it + 4 else it }
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

    val displayedStage = (Math.round(rotation / 180f) % 4).let { if (it < 0) it + 4 else it }
    val contentRotation = if ((rotation % 360f).let { if (it < 0f) it + 360f else it } in 90f..270f) 180f else 0f

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF8B5CF6), Color(0xFF4C1D95)) // Purple gradient matching image
    )

    val mainTitle = when (displayedStage) {
        1 -> "Stage-1 (Jul 2026)"
        2 -> "Stage-2 (Jan 2027)"
        3 -> "Final Basic (2027)"
        else -> "Converted Basic (2026)"
    }
    
    val mainAmount = when (displayedStage) {
        1 -> result.stage1
        2 -> result.stage2
        3 -> result.convertedBasic
        else -> result.convertedBasic
    }
    
    val subTitle1 = when (displayedStage) {
        1 -> "Old Basic"
        2 -> "Old Basic"
        3 -> "Old Basic"
        else -> "Stage-1 (Jul 2026)"
    }
    
    val subAmount1 = when (displayedStage) {
        1 -> result.currentBasic
        2 -> result.currentBasic
        3 -> result.currentBasic
        else -> result.stage1
    }
    
    val subTitle2 = when (displayedStage) {
        1 -> "Increase (50%)"
        2 -> "Increase (75%)"
        3 -> "Increase (100%)"
        else -> "Stage-2 (Jan 2027)"
    }
    
    val subAmount2 = when (displayedStage) {
        1 -> result.stage1 - result.currentBasic
        2 -> result.stage2 - result.currentBasic
        3 -> result.convertedBasic - result.currentBasic
        else -> result.stage2
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                // 3D Parallax Tilt based on scroll + Flip rotation
                rotationX = -(scrollOffset * 0.015f).coerceIn(-5f, 5f)
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = RoundedCornerShape(32.dp),
        color = Color.Transparent,
        shadowElevation = 16.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBrush)
                .graphicsLayer {
                    rotationY = contentRotation
                }
        ) {
            // Glassmorphism decorative circles for premium look
            Box(
                modifier = Modifier
                    .offset(x = 200.dp, y = (-40).dp)
                    .size(150.dp)
                    .background(Color.White.copy(alpha = 0.1f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .offset(x = (-30).dp, y = 150.dp)
                    .size(100.dp)
                    .background(Color.White.copy(alpha = 0.1f), CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = mainTitle,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        AnimatedCounter(
                            targetValue = mainAmount.toFloat(),
                            prefix = "৳",
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    
                    // Increase percentage badge
                    val diff = mainAmount - result.currentBasic
                    val percentage = (diff.toDouble() / result.currentBasic * 100).coerceAtLeast(0.0)
                    
                    Surface(
                        color = SuccessGreen,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(top = 4.dp),
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = "Increase",
                                modifier = Modifier.size(12.dp),
                                tint = Color.White
                            )
                            AnimatedCounter(
                                targetValue = percentage.toFloat(),
                                suffix = "%",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                isDecimal = true
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(28.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = subTitle1,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        AnimatedCounter(
                            targetValue = subAmount1.toFloat(),
                            prefix = "৳",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = subTitle2,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        AnimatedCounter(
                            targetValue = subAmount2.toFloat(),
                            prefix = "৳",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}
INNER_EOF
