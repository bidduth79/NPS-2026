package com.example.ui.components.chart

import com.example.ui.theme.ChartPurple

import com.example.ui.theme.ChartOrange

import com.example.ui.theme.ChartGreen

import com.example.ui.theme.ChartRed

import com.example.ui.theme.DarkBorder

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.utils.CalculationResult

@Composable
fun SalaryGraphCanvas(result: CalculationResult) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        val animationProgress = remember { Animatable(0f) }
        LaunchedEffect(Unit) {
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(1200, easing = FastOutSlowInEasing)
            )
        }
        val borderColor = DarkBorder
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            if (w <= 0f || h <= 0f) return@Canvas

            val data = listOf(
                result.currentBasic.toFloat(),
                result.stage1.toFloat(),
                result.stage2.toFloat(),
                result.convertedBasic.toFloat()
            )
            
            val maxVal = data.maxOrNull() ?: 1f
            val minVal = (data.minOrNull() ?: 0f) * 0.9f // small buffer
            val range = (maxVal - minVal).coerceAtLeast(1f)

            val barWidth = w / 7f
            val spacing = (w - (barWidth * 4)) / 3f

            val colors = listOf(
                ChartRed, // Red
                ChartGreen, // Green
                ChartOrange, // Orange
                ChartPurple  // Purple
            )

            // Draw Grid lines
            val gridLines = 4
            for (i in 0..gridLines) {
                val y = h * (i / gridLines.toFloat())
                drawLine(
                    color = borderColor,
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1f
                )
            }

            // Draw Bars
            for (i in 0..3) {
                val value = data[i]
                val normalizedHeight = ((value - minVal) / range) * h * 0.8f + (h * 0.1f) // Ensure minimum height
                val animatedHeight = normalizedHeight * animationProgress.value
                val x = i * (barWidth + spacing)
                val y = h - animatedHeight

                if (animatedHeight > 0f && h > 0f) {
                    // Bar background gradient
                    val brush = Brush.verticalGradient(
                        colors = listOf(colors[i], colors[i].copy(alpha = 0.5f)),
                        startY = y,
                        endY = h + 0.1f
                    )

                    drawRoundRect(
                        brush = brush,
                        topLeft = Offset(x, y),
                        size = Size(barWidth, animatedHeight),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )
                }
            }
            
            // Draw Line connecting tops
            if (animationProgress.value > 0) {
                val path = Path()
                for (i in 0..3) {
                    val value = data[i]
                    val normalizedHeight = ((value - minVal) / range) * h * 0.8f + (h * 0.1f)
                    val animatedHeight = normalizedHeight * animationProgress.value
                    val x = i * (barWidth + spacing) + barWidth / 2f
                    val y = h - animatedHeight
                    
                    if (i == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                    
                    // Draw point
                    drawCircle(
                        color = Color.White,
                        radius = 4.dp.toPx() * animationProgress.value,
                        center = Offset(x, y)
                    )
                }

                drawPath(
                    path = path,
                    color = Color.White.copy(alpha = 0.5f),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}
