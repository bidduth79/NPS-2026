package com.example.ui.components.gpf

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BlueAccent
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkCard

@Composable
fun GpfInputCard(
    initialBalance: String,
    onInitialBalanceChange: (String) -> Unit,
    monthlySubscription: String,
    onMonthlySubscriptionChange: (String) -> Unit,
    targetYear: String,
    onTargetYearChange: (String) -> Unit,
    onCalculateClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = DarkCard.copy(alpha = 0.8f),
        border = BorderStroke(1.dp, DarkBorder.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = initialBalance,
                onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) onInitialBalanceChange(it) },
                label = { Text("Opening Balance", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = BlueAccent,
                    unfocusedBorderColor = DarkBorder
                )
            )
            OutlinedTextField(
                value = monthlySubscription,
                onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) onMonthlySubscriptionChange(it) },
                label = { Text("Monthly Subscription", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = BlueAccent,
                    unfocusedBorderColor = DarkBorder
                )
            )
            OutlinedTextField(
                value = targetYear,
                onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) onTargetYearChange(it) },
                label = { Text("Target Year (e.g. 2050)", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = BlueAccent,
                    unfocusedBorderColor = DarkBorder
                )
            )
            Button(
                onClick = onCalculateClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueAccent),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Calculate GPF", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
