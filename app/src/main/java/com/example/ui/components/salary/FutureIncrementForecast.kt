package com.example.ui.components.salary

import androidx.compose.foundation.background
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
import com.example.data.PayScale2015
import com.example.data.PayScale2026
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.DarkCard
import com.example.ui.theme.TextGray
import java.util.Calendar

@Composable
fun FutureIncrementForecast(
    grade: Int,
    currentStepIndex: Int,
    isScale2026: Boolean
) {
    val stepsList = if (isScale2026) PayScale2026.steps[grade] else PayScale2015.steps[grade]
    if (stepsList == null || currentStepIndex < 0 || currentStepIndex >= stepsList.size) return

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Card(
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Future Increment Forecast", 
                color = AccentPurpleLight, 
                fontSize = 18.sp, 
                fontWeight = FontWeight.Bold
            )
            Text(
                "Projected basic salary for the next 5 years based on the selected scale.", 
                color = TextGray, 
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 4.dp)
            )

            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Year", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Step", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Basic Salary", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.5f), textAlign = androidx.compose.ui.text.style.TextAlign.End)
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            // Forecast Rows
            for (i in 1..5) {
                val nextStepIndex = currentStepIndex + i
                val yearStr = "${currentYear + i} (July)"
                
                if (nextStepIndex < stepsList.size) {
                    val amount = stepsList[nextStepIndex]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(yearStr, color = TextGray, fontSize = 14.sp, modifier = Modifier.weight(1f))
                        Text("Step ${nextStepIndex + 1}", color = Color.White, fontSize = 14.sp, modifier = Modifier.weight(1f))
                        Text(
                            "৳$amount", 
                            color = AccentPurple, 
                            fontSize = 14.sp, 
                            fontWeight = FontWeight.Bold, 
                            modifier = Modifier.weight(1.5f), 
                            textAlign = androidx.compose.ui.text.style.TextAlign.End
                        )
                    }
                    if (i < 5 && nextStepIndex < stepsList.size - 1) {
                        HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Maximum step reached for Grade $grade.", color = TextGray, fontSize = 12.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                    }
                    break
                }
            }
        }
    }
}
