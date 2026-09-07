package com.example.ui.components.gpf

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.BlueAccent
import com.example.utils.GpfCalculator
import com.example.utils.GpfYearResult
import com.example.utils.pdf.GpfPdfGenerator
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun GpfScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    var initialBalance by remember { mutableStateOf("1384348") }
    var monthlySubscription by remember { mutableStateOf("4733") }
    
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    var targetYear by remember { mutableStateOf((currentYear + 1).toString()) }

    var results by remember { mutableStateOf<List<GpfYearResult>>(emptyList()) }

    fun calculate() {
        val balance = initialBalance.toLongOrNull() ?: 0L
        val sub = monthlySubscription.toLongOrNull() ?: 0L
        val tYear = targetYear.toIntOrNull() ?: (currentYear + 1)
        val sYear = currentYear
        
        if (tYear >= sYear) {
            results = GpfCalculator.calculate(sYear, tYear + 1, balance, sub)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        GpfHeader(
            onBack = onBack,
            onDownloadClick = {
                if (results.isNotEmpty()) {
                    coroutineScope.launch {
                        GpfPdfGenerator.generatePdf(context, results)
                    }
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GpfInputCard(
                    initialBalance = initialBalance,
                    onInitialBalanceChange = { initialBalance = it },
                    monthlySubscription = monthlySubscription,
                    onMonthlySubscriptionChange = { monthlySubscription = it },
                    targetYear = targetYear,
                    onTargetYearChange = { targetYear = it },
                    onCalculateClick = { calculate() }
                )
            }

            if (results.isNotEmpty()) {
                item {
                    Text(
                        text = "Calculation Results",
                        color = androidx.compose.ui.graphics.Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(results) { result ->
                    GpfYearCard(result)
                }
            }
        }
    }
}
