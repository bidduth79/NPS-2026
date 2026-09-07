package com.example.ui.components

import com.example.ui.theme.BluePrimary

import com.example.ui.theme.SuccessGreen

import com.example.ui.theme.ChartOrange

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun HeroIllustration(scrollOffset: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        
        // Calculator Icon
        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-30).dp, y = (-40).dp)
                .graphicsLayer {
                    translationY = -(scrollOffset * 0.7f)
                    rotationZ = -15f + (scrollOffset * 0.05f)
                },
            shape = RoundedCornerShape(16.dp),
            color = Color.White.copy(alpha = 0.9f),
            shadowElevation = 8.dp
        ) {
            Icon(
                imageVector = Icons.Rounded.Calculate,
                contentDescription = "Calculator",
                modifier = Modifier
                    .padding(12.dp)
                    .size(36.dp),
                tint = BluePrimary // Blue
            )
        }
        
        // Money Icon
        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-80).dp, y = 40.dp)
                .graphicsLayer {
                    translationY = -(scrollOffset * 0.35f)
                    rotationZ = 10f - (scrollOffset * 0.02f)
                },
            shape = CircleShape,
            color = ChartOrange.copy(alpha = 0.9f), // Gold
            shadowElevation = 12.dp
        ) {
            Icon(
                imageVector = Icons.Rounded.MonetizationOn,
                contentDescription = "Money",
                modifier = Modifier
                    .padding(10.dp)
                    .size(28.dp),
                tint = Color.White
            )
        }
        
        // Growth Arrow Icon
        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-95).dp, y = (-50).dp)
                .graphicsLayer {
                    translationY = -(scrollOffset * 0.55f)
                    rotationZ = 5f + (scrollOffset * 0.03f)
                },
            shape = RoundedCornerShape(12.dp),
            color = SuccessGreen.copy(alpha = 0.9f), // Emerald Green
            shadowElevation = 10.dp
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.TrendingUp,
                contentDescription = "Growth",
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp),
                tint = Color.White
            )
        }
    }
}
