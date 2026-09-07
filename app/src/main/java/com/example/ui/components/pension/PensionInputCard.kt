package com.example.ui.components.pension

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.DarkCard
import com.example.ui.theme.TextGray

@Composable
fun PensionInputCard(
    basicSalaryStr: String,
    onBasicSalaryChange: (String) -> Unit,
    yearsOfService: Float,
    onYearsOfServiceChange: (Float) -> Unit,
    accruedLeaveMonths: Float,
    onAccruedLeaveMonthsChange: (Float) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Basic Salary (শেষ মূল বেতন)",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = basicSalaryStr,
                onValueChange = { if (it.all { char -> char.isDigit() }) onBasicSalaryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPurple,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = AccentPurple
                ),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Years of Service (চাকরির বয়স)",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "${yearsOfService.toInt()} Years",
                    color = AccentPurpleLight,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = yearsOfService,
                onValueChange = onYearsOfServiceChange,
                valueRange = 10f..40f,
                steps = 29, // (40 - 10) - 1
                colors = SliderDefaults.colors(
                    thumbColor = AccentPurpleLight,
                    activeTrackColor = AccentPurple,
                    inactiveTrackColor = Color.White.copy(alpha = 0.1f)
                )
            )
            Text(
                "Note: Minimum 10 years required for monthly pension.",
                color = TextGray,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Unused Leave (অর্জিত ছুটি)",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "${accruedLeaveMonths.toInt()} Months",
                    color = AccentPurpleLight,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = accruedLeaveMonths,
                onValueChange = onAccruedLeaveMonthsChange,
                valueRange = 0f..18f,
                steps = 17, // (18 - 0) - 1
                colors = SliderDefaults.colors(
                    thumbColor = AccentPurpleLight,
                    activeTrackColor = AccentPurple,
                    inactiveTrackColor = Color.White.copy(alpha = 0.1f)
                )
            )
            Text(
                "Note: Max 18 months allowable for encashment.",
                color = TextGray,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
