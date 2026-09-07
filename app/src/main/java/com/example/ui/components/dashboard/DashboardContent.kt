package com.example.ui.components.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AnimatedCounter
import com.example.ui.theme.SuccessGreen

@Composable
fun CompactDashboardContent(state: DashboardState, percentage: Double, displayedStage: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f).padding(end = 4.dp)
        ) {
            Text(
                text = state.mainTitle,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            AnimatedCounter(animationKey = displayedStage, 
                targetValue = state.mainAmount.toFloat(),
                prefix = "৳",
                color = state.mainAmountColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
        }
        
        Surface(
            color = SuccessGreen,
            shape = RoundedCornerShape(50),
            shadowElevation = 4.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = "Increase",
                    modifier = Modifier.size(8.dp),
                    tint = Color.White
                )
                AnimatedCounter(animationKey = displayedStage, 
                    targetValue = percentage.toFloat(),
                    suffix = "%",
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Black,
                    isDecimal = true
                )
            }
        }
    }
}

@Composable
fun ExpandedDashboardContent(state: DashboardState, percentage: Double, displayedStage: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = state.mainTitle,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            AnimatedCounter(animationKey = displayedStage, 
                targetValue = state.mainAmount.toFloat(),
                prefix = "৳",
                color = state.mainAmountColor,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
        }
        
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
                AnimatedCounter(animationKey = displayedStage, 
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
}
