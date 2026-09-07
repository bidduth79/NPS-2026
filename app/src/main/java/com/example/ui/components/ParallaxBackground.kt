package com.example.ui.components

import com.example.ui.theme.BluePrimary

import com.example.ui.theme.AccentPurple

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ParallaxBackground(scrollOffset: Int, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Background Orb 1 (Top Left) - Moves fast
        val orb1Y = height * 0.1f - (scrollOffset * 0.4f)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF4C1D95).copy(alpha = 0.6f), Color.Transparent),
                center = Offset(x = width * 0.1f, y = orb1Y),
                radius = width * 0.7f
            ),
            radius = width * 0.7f,
            center = Offset(x = width * 0.1f, y = orb1Y)
        )

        // Background Orb 2 (Bottom Right) - Moves slower (parallax depth)
        val orb2Y = height * 0.8f - (scrollOffset * 0.15f)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(AccentPurple.copy(alpha = 0.4f), Color.Transparent),
                center = Offset(x = width * 0.9f, y = orb2Y),
                radius = width * 0.6f
            ),
            radius = width * 0.6f,
            center = Offset(x = width * 0.9f, y = orb2Y)
        )

        // Background Orb 3 (Center Left) - Moves very fast
        val orb3Y = height * 0.5f - (scrollOffset * 0.6f)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(BluePrimary.copy(alpha = 0.2f), Color.Transparent),
                center = Offset(x = -width * 0.2f, y = orb3Y),
                radius = width * 0.5f
            ),
            radius = width * 0.5f,
            center = Offset(x = -width * 0.2f, y = orb3Y)
        )
    }
}
