package com.example.ui.components.calculation

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.TextGray

import com.example.ui.theme.DarkBorder

import com.example.ui.theme.DarkCard

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BeachHousesIllustration

@Composable
fun CalculationDetailsHeader(
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(percent = 50),
        color = DarkCard.copy(alpha = 0.8f),
        border = BorderStroke(1.dp, DarkBorder.copy(alpha = 0.5f)),
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(id = R.string.calculation_details),
                    color = TextGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            // Animated toggle icon and Beach houses
            Row(verticalAlignment = Alignment.CenterVertically) {
                val infiniteTransition = rememberInfiniteTransition(label = "bounce")
                val bounceOffset by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = if (isExpanded) 4f else -4f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "bounceAnim"
                )
                
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Toggle",
                    tint = TextGray,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .offset(y = bounceOffset.dp)
                )
                BeachHousesIllustration()
            }
        }
    }
}
