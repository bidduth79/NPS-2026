package com.example.ui.components.pension

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.TextGray
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PensionResultCard(
    basicSalary: Long,
    percentage: Double,
    gratuityRate: Long,
    accruedLeaveMonths: Float,
    leaveEncashment: Long,
    lumpSumGratuity: Long,
    totalLumpSum: Long,
    monthlyPension: Long
) {
    val formatter = NumberFormat.getNumberInstance(Locale("en", "IN"))
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AccentPurple.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, AccentPurple.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                "Calculated Pension & Gratuity",
                color = AccentPurpleLight,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            ResultRow("Basic Used", "৳${formatter.format(basicSalary)}")
            ResultRow("Pensionable %", "${(percentage * 100).toInt()}%")
            ResultRow("Gratuity Rate (Per 1 Tk)", "৳$gratuityRate")
            
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 12.dp))
            
            Text("Leave Encashment / ছুটি নগদায়ন (${accruedLeaveMonths.toInt()} মাস)", color = TextGray, fontSize = 14.sp)
            Text(
                "৳${formatter.format(leaveEncashment)}", 
                color = Color.White, 
                fontSize = 20.sp, 
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text("Gratuity / এককালীন আনুতোষিক", color = TextGray, fontSize = 14.sp)
            Text(
                "৳${formatter.format(lumpSumGratuity)}", 
                color = Color.White, 
                fontSize = 20.sp, 
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            HorizontalDivider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 12.dp))

            Text("Total Lump Sum / মোট এককালীন প্রাপ্য", color = AccentPurpleLight, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(
                "৳${formatter.format(totalLumpSum)}", 
                color = AccentPurpleLight, 
                fontSize = 28.sp, 
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text("Monthly Pension / মাসিক পেনশন", color = TextGray, fontSize = 14.sp)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    "৳${formatter.format(monthlyPension)}", 
                    color = Color(0xFF10B981), // Emerald green
                    fontSize = 28.sp, 
                    fontWeight = FontWeight.Bold
                )
                Text(
                    " / month", 
                    color = TextGray, 
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                )
            }
            Text(
                "Includes ৳1,500 Medical (৳2,500 for age 65+)", 
                color = TextGray, 
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextGray, fontSize = 14.sp)
        Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}
