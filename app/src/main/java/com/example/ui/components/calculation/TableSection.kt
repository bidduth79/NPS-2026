package com.example.ui.components.calculation

import com.example.ui.theme.DarkBorder

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter
import kotlinx.coroutines.delay

@Composable
fun TableSection(
    result: CalculationResult,
    stageNumber: Int,
    rateStr: String,
    rate: Double,
    newBasic: Long,
    date: String,
    headerColor: Color
) {
    val scrollState = rememberScrollState()
    
    LaunchedEffect(Unit) {
        delay(400) // Wait for the AnimatedVisibility to finish expanding
        scrollState.animateScrollTo(100)
        delay(100)
        scrollState.animateScrollTo(0)
    }
    
    // We add horizontal scroll to the table content
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max).defaultMinSize(minWidth = 460.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("STEP", modifier = Modifier.width(36.dp), fontSize = 10.sp, color = headerColor, fontWeight = FontWeight.Bold)
                Text("DESCRIPTION", modifier = Modifier.weight(1f).padding(end = 8.dp), fontSize = 10.sp, color = headerColor, fontWeight = FontWeight.Bold)
                Text("CALCULATION", modifier = Modifier.width(130.dp).padding(end = 4.dp), fontSize = 10.sp, color = headerColor, fontWeight = FontWeight.Bold)
                Text("AMOUNT (৳)", modifier = Modifier.width(70.dp), textAlign = TextAlign.End, fontSize = 10.sp, color = headerColor, fontWeight = FontWeight.Bold)
            }
            
            HorizontalDivider(color = DarkBorder)
            
            // Step 1
            TableRow("1", "Old pay - Old minimum pay (30-06-26)", "${NumberFormatter.format(result.currentBasic)} - ${NumberFormatter.format(result.details.firstStep2015)}", result.details.accruedIncrease)
            
            // Step 2
            TableRow("2", "New minimum pay\nPublished NPS 2026 minimum for this grade", "-", result.details.firstStep2026)
            
            // Step 3
            TableRow("3", "New minimum + Step 1", "${NumberFormatter.format(result.details.firstStep2026)} + ${NumberFormatter.format(result.details.accruedIncrease)}", result.details.target)
            
            // Step 4
            TableRow("4", "Matching / next higher stage on NPS 2026\nNext higher than ${NumberFormatter.format(result.details.target)}", "-", result.details.nextStep2026Target)
            
            // Step 5
            TableRow("5", "Next stage after Step 4 (Fact Increment 01-07-2026)\nNext stage after ${NumberFormatter.format(result.details.nextStep2026Target)}", "-", result.details.finalConvertedBasic)
            
            // Step 6
            val diff = result.details.finalConvertedBasic - result.currentBasic
            val step6Amt = newBasic - result.currentBasic
            TableRow("6", "(Step 5 - Old pay) x $rateStr\nRate $rateStr (grades ${if (result.grade in 10..20) "10-20" else "1-9"})", "(${NumberFormatter.format(result.details.finalConvertedBasic)} - ${NumberFormatter.format(result.currentBasic)}) x $rateStr", step6Amt)
            
            // Step 7
            TableRow("7", "Old pay + Step 6 (new basic) ($date)", "${NumberFormatter.format(result.currentBasic)} + ${NumberFormatter.format(step6Amt)}", newBasic)
        }
    }
}
