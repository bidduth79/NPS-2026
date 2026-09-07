package com.example.ui.components.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TransactionEntity
import com.example.ui.theme.RedAccent
import com.example.ui.theme.SuccessGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionRow(tx: TransactionEntity) {
    val isOut = tx.type == "OUT"
    val color = if (isOut) RedAccent else SuccessGreen
    val icon = if (isOut) Icons.Rounded.ArrowUpward else Icons.Rounded.ArrowDownward
    
    val dateString = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(Date(tx.date))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(tx.medium.take(1).uppercase(), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(tx.medium, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(dateString, color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
            }
        }
        Text(
            text = "${if (isOut) "-" else "+"}৳${String.format("%.0f", tx.amount)}",
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
