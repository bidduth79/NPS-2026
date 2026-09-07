package com.example.ui.components.chart

import com.example.ui.theme.TextGray

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utils.NumberFormatter

data class ChartBarData(val label: String, val value: Long, val color: Color)

@Composable
fun ChartInfoScreen(title: String, isCompact: Boolean, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = if (isCompact) 12.sp else 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        content()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tap to flip",
            color = TextGray,
            fontSize = if (isCompact) 8.sp else 10.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun InfoRow(label: String, value: Long, color: Color, isCompact: Boolean, isBold: Boolean = false, prefix: String = "৳") {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = if (isCompact) 10.sp else 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "$prefix${NumberFormatter.format(value)}",
            color = color,
            fontSize = if (isCompact) 10.sp else 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium
        )
    }
}
