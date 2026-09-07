package com.example.ui.components.pension

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Elderly
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCard
import com.example.ui.theme.TextGray
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PensionScreen(onBack: () -> Unit) {
    var basicSalaryStr by remember { mutableStateOf("45000") }
    var yearsOfService by remember { mutableFloatStateOf(25f) }
    var accruedLeaveMonths by remember { mutableFloatStateOf(18f) }

    val basicSalary = basicSalaryStr.toLongOrNull() ?: 0L

    // Pension Calculation Logic (National Pay Scale 2015 BD)
    val percentage = when (yearsOfService.toInt()) {
        in 0..4 -> 0.0
        in 5..9 -> 0.0 // Simplified: standard pension starts at 10
        10 -> 0.51
        11 -> 0.54
        12 -> 0.57
        13 -> 0.60
        14 -> 0.63
        15 -> 0.65
        16 -> 0.69
        17 -> 0.72
        18 -> 0.75
        19 -> 0.78
        20 -> 0.81
        21 -> 0.84
        22 -> 0.87
        23, 24 -> 0.90
        else -> 0.90 // 25+ years
    }

    val gratuityRate = when (yearsOfService.toInt()) {
        in 0..9 -> 0L
        in 10..14 -> 260L
        in 15..19 -> 245L
        else -> 230L // 20+ years
    }

    val grossPension = (basicSalary * percentage).toLong()
    val surrendered = grossPension / 2
    val retained = grossPension - surrendered 

    val lumpSumGratuity = surrendered * gratuityRate
    val leaveEncashment = basicSalary * accruedLeaveMonths.toLong() // ছুটি নগদায়ন
    val totalLumpSum = lumpSumGratuity + leaveEncashment

    val medicalAllowance = 1500L
    val monthlyPension = retained + medicalAllowance

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Top App Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Pension & Gratuity",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 120.dp), // padding for bottom nav
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon Header
            Icon(
                imageVector = Icons.Rounded.Elderly,
                contentDescription = "Pension",
                tint = AccentPurpleLight,
                modifier = Modifier
                    .size(64.dp)
                    .background(AccentPurple.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Inputs Card
            PensionInputCard(
                basicSalaryStr = basicSalaryStr,
                onBasicSalaryChange = { basicSalaryStr = it },
                yearsOfService = yearsOfService,
                onYearsOfServiceChange = { yearsOfService = it },
                accruedLeaveMonths = accruedLeaveMonths,
                onAccruedLeaveMonthsChange = { accruedLeaveMonths = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Results Card
            PensionResultCard(
                basicSalary = basicSalary,
                percentage = percentage,
                gratuityRate = gratuityRate,
                accruedLeaveMonths = accruedLeaveMonths,
                leaveEncashment = leaveEncashment,
                lumpSumGratuity = lumpSumGratuity,
                totalLumpSum = totalLumpSum,
                monthlyPension = monthlyPension
            )
        }
    }
}


