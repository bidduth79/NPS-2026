package com.example.ui.components.gpf

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BlueAccent
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkCard
import com.example.utils.GpfYearResult
import com.example.utils.NumberFormatter

@Composable
fun GpfYearCard(result: GpfYearResult) {
    var isExpanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = DarkCard,
        border = BorderStroke(1.dp, DarkBorder),
        modifier = Modifier.fillMaxWidth().clickable { isExpanded = !isExpanded }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FY ${result.fiscalYear}",
                    color = BlueAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = Color.Gray
                )
            }

            HorizontalDivider(color = DarkBorder, modifier = Modifier.padding(vertical = 4.dp))
            
            GpfRow("Opening Balance", result.openingBalance)
            GpfRow("Yearly Deposit", result.totalSubscription)
            GpfRow("Profit (${(result.profitRate * 100).toInt()}%)", result.profit)
            
            HorizontalDivider(color = DarkBorder, modifier = Modifier.padding(vertical = 4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Closing Balance", color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    "৳${NumberFormatter.format(result.closingBalance)}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Monthly Breakdown", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)

                    result.monthlyDetails.forEach { month ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(month.monthName, color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.weight(1f))
                            Text("Op: ${NumberFormatter.format(month.openingBalance)}", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.weight(1.5f))
                            Text("Dep: ${NumberFormatter.format(month.deposit)}", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.weight(1.2f))
                            Text("Cl: ${NumberFormatter.format(month.closingBalance)}", color = Color.White, fontSize = 12.sp, modifier = Modifier.weight(1.5f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GpfRow(label: String, amount: Long) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text("৳${NumberFormatter.format(amount)}", color = Color.White, fontSize = 14.sp)
    }
}
