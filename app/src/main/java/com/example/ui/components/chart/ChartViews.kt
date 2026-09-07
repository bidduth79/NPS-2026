package com.example.ui.components.chart

import com.example.ui.theme.TextGray

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AnimatedCounter
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter

@Composable
fun FrontBarChart(
    isCompact: Boolean,
    bars: List<ChartBarData>,
    selectedIndex: Int,
    maxSalary: Float,
    animationProgress: Float
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Salary Growth Chart",
            color = Color.White,
            fontSize = if (isCompact) 10.sp else 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        
        Spacer(modifier = Modifier.height(if (isCompact) 10.dp else 32.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            bars.forEachIndexed { index, barData ->
                val isSelected = selectedIndex == index
                val targetHeight = (barData.value.toFloat() / maxSalary)
                val animatedHeight = targetHeight * animationProgress
                
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1.0f,
                    animationSpec = tween(durationMillis = 300),
                    label = "bar_scale"
                )
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    AnimatedCounter(
                        targetValue = (barData.value / 1000).toFloat(),
                        suffix = "k",
                        fontSize = if (isSelected) (if (isCompact) 10.sp else 12.sp) else (if (isCompact) 8.sp else 10.sp),
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) barData.color else TextGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val animatedFraction = animatedHeight.coerceIn(0.01f, 1f)
                        Box(
                            modifier = Modifier
                                .width(if (isCompact) (if (isSelected) 24.dp else 16.dp) else (if (isSelected) 44.dp else 36.dp))
                                .fillMaxHeight(animatedFraction)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    transformOrigin = androidx.compose.ui.graphics.TransformOrigin(0.5f, 1f)
                                }
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(barData.color)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier.height(24.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            text = barData.label,
                            fontSize = if (isCompact) 8.sp else 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) barData.color else TextGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
