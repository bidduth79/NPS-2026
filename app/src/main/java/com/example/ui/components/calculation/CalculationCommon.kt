package com.example.ui.components.calculation

import com.example.ui.theme.GrayLight

import com.example.ui.theme.TextGray

import com.example.ui.theme.DarkBorder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utils.NumberFormatter

@Composable
fun SummaryBox(
    title1: String, 
    title2: String, 
    amount: Long, 
    modifier: Modifier = Modifier, 
    bgColor: Color, 
    borderColor: Color, 
    amountColor: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = bgColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title1,
                fontSize = 9.sp,
                color = TextGray,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = title2,
                fontSize = 9.sp,
                color = TextGray,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "৳ ${NumberFormatter.format(amount)}",
                fontSize = 14.sp,
                color = amountColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TableRow(step: String, desc: String, calc: String, amt: Long) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(step, modifier = Modifier.width(36.dp), fontSize = 12.sp, color = TextGray, fontWeight = FontWeight.Medium)
        Text(desc, modifier = Modifier.weight(1f).padding(end = 8.dp), fontSize = 12.sp, color = GrayLight, lineHeight = 18.sp)
        Text(calc, modifier = Modifier.width(130.dp).padding(end = 4.dp), fontSize = 12.sp, color = TextGray)
        Text(NumberFormatter.format(amt), modifier = Modifier.width(70.dp), textAlign = TextAlign.End, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
    HorizontalDivider(color = DarkBorder.copy(alpha = 0.5f))
}
