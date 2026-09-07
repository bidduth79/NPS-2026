package com.example.ui.components.chart

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.ChartPurple

import com.example.ui.theme.ChartOrange

import com.example.ui.theme.ChartGreen

import com.example.ui.theme.ChartRed

import com.example.ui.theme.DarkBorder

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter

@Composable
fun BackLineChart(
    isCompact: Boolean,
    result: CalculationResult,
    animationProgress: Float
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Progression Timeline",
            color = Color.White,
            fontSize = if (isCompact) 10.sp else 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(if (isCompact) 8.dp else 16.dp))

        val borderColor = DarkBorder
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
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
            val minVal = (data.minOrNull() ?: 0f) * 0.9f
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
                val normalizedHeight = ((value - minVal) / range) * h * 0.8f + (h * 0.1f)
                val animatedHeight = normalizedHeight * animationProgress
                val x = i * (barWidth + spacing)
                val y = h - animatedHeight

                if (animatedHeight > 0f && h > 0f) {
                    val brush = Brush.verticalGradient(
                        colors = listOf(colors[i], colors[i].copy(alpha = 0.5f)),
                        startY = y,
                        endY = h + 0.1f
                    )

                    drawRoundRect(
                        brush = brush,
                        topLeft = Offset(x, y),
                        size = Size(barWidth, animatedHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )
                }
            }
            
            // Draw Line connecting tops
            if (animationProgress > 0) {
                val path = Path()
                for (i in 0..3) {
                    val value = data[i]
                    val normalizedHeight = ((value - minVal) / range) * h * 0.8f + (h * 0.1f)
                    val animatedHeight = normalizedHeight * animationProgress
                    val x = i * (barWidth + spacing) + barWidth / 2f
                    val y = h - animatedHeight
                    
                    if (i == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                    
                    drawCircle(
                        color = Color.White,
                        radius = 3.dp.toPx() * animationProgress,
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

        Spacer(modifier = Modifier.height(12.dp))
        
        // Labels for Back Side
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val labels = listOf("Old", "Stg 1", "Stg 2", stringResource(id = R.string.chart_final))
            val values = listOf(result.currentBasic, result.stage1, result.stage2, result.convertedBasic)
            val colors = listOf(ChartRed, ChartGreen, ChartOrange, ChartPurple)
            
            for (i in 0..3) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Text(
                        text = labels[i],
                        color = colors[i],
                        fontSize = if (isCompact) 8.sp else 10.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = NumberFormatter.format(values[i]),
                        color = Color.White,
                        fontSize = if (isCompact) 8.sp else 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
