package com.example.ui.components.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AnimatedCounter

@Composable
fun DashboardSubAmounts(
    state: DashboardState,
    isCompact: Boolean,
    displayedStage: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = state.subTitle1,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = if (isCompact) 8.sp else 11.sp,
                fontWeight = FontWeight.Bold
            )
            AnimatedCounter(
                animationKey = displayedStage, 
                targetValue = state.subAmount1.toFloat(),
                prefix = "৳",
                color = state.subAmount1Color,
                fontSize = if (isCompact) 12.sp else 20.sp,
                fontWeight = FontWeight.Black
            )
        }
        
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = state.subTitle2,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = if (isCompact) 8.sp else 11.sp,
                fontWeight = FontWeight.Bold
            )
            AnimatedCounter(
                animationKey = displayedStage, 
                targetValue = state.subAmount2.toFloat(),
                prefix = "৳",
                color = Color.White,
                fontSize = if (isCompact) 12.sp else 20.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
