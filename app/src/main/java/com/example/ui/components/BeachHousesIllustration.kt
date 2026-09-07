package com.example.ui.components

import com.example.ui.theme.BluePrimary

import com.example.ui.theme.ErrorRed

import com.example.ui.theme.ChartOrange

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

@Composable
fun BeachHousesIllustration(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "sway")
    val swayAngle by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "swayAnim"
    )

    Canvas(modifier = modifier.size(width = 72.dp, height = 36.dp)) {
        val w = size.width
        val h = size.height

        // Trees (Circles in background) with Swaying Animation
        withTransform({
            rotate(swayAngle, pivot = Offset(w * 0.3f, h * 0.5f))
        }) {
            drawCircle(color = Color(0xFF65A30D), radius = h * 0.25f, center = Offset(w * 0.3f, h * 0.3f))
            drawCircle(color = Color(0xFF4D7C0F), radius = h * 0.2f, center = Offset(w * 0.4f, h * 0.25f))
        }
        withTransform({
            rotate(-swayAngle * 0.8f, pivot = Offset(w * 0.75f, h * 0.5f))
        }) {
            drawCircle(color = Color(0xFF65A30D), radius = h * 0.3f, center = Offset(w * 0.7f, h * 0.25f))
            drawCircle(color = Color(0xFF4D7C0F), radius = h * 0.2f, center = Offset(w * 0.8f, h * 0.35f))
        }

        // Ground / Water / Sand
        val sandPath = Path().apply {
            moveTo(0f, h * 0.8f)
            quadraticTo(w * 0.5f, h * 0.65f, w, h * 0.85f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(sandPath, color = Color(0xFFD4A373)) // Sand

        val waterPath = Path().apply {
            moveTo(0f, h * 0.85f)
            quadraticTo(w * 0.5f, h * 0.95f, w, h * 0.85f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(waterPath, color = Color(0xFF38BDF8)) // Water
        
        val waterPath2 = Path().apply {
            moveTo(w * 0.1f, h * 0.95f)
            quadraticTo(w * 0.6f, h * 1.05f, w * 0.9f, h * 0.9f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(waterPath2, color = Color(0xFF0284C7)) // Darker Water

        // Function to draw a house
        fun drawHouse(x: Float, y: Float, width: Float, height: Float, bodyColor: Color, roofColor: Color) {
            // Body
            drawRect(color = bodyColor, topLeft = Offset(x, y), size = Size(width, height))
            // Roof
            val roofPath = Path().apply {
                moveTo(x - width * 0.15f, y)
                lineTo(x + width * 0.5f, y - height * 0.5f)
                lineTo(x + width * 1.15f, y)
                close()
            }
            drawPath(roofPath, color = roofColor)
            // Door
            drawRect(
                color = Color.DarkGray, 
                topLeft = Offset(x + width * 0.3f, y + height * 0.4f), 
                size = Size(width * 0.4f, height * 0.6f)
            )
        }

        // House 1 (Blue with Red roof)
        drawHouse(x = w * 0.05f, y = h * 0.45f, width = w * 0.18f, height = h * 0.35f, bodyColor = BluePrimary, roofColor = ErrorRed)
        
        // House 2 (Purple with Green roof)
        drawHouse(x = w * 0.28f, y = h * 0.55f, width = w * 0.12f, height = h * 0.25f, bodyColor = Color(0xFFA855F7), roofColor = Color(0xFF84CC16))
        
        // House 3 (Light Blue with Gray roof)
        drawHouse(x = w * 0.45f, y = h * 0.5f, width = w * 0.15f, height = h * 0.3f, bodyColor = Color(0xFF93C5FD), roofColor = Color(0xFF64748B))
        
        // House 4 (Orange with Yellow roof)
        drawHouse(x = w * 0.65f, y = h * 0.45f, width = w * 0.15f, height = h * 0.35f, bodyColor = Color(0xFFF97316), roofColor = ChartOrange)
        
        // House 5 (Teal with Dark Blue roof)
        drawHouse(x = w * 0.82f, y = h * 0.4f, width = w * 0.15f, height = h * 0.4f, bodyColor = Color(0xFF2DD4BF), roofColor = Color(0xFF1E3A8A))
    }
}
