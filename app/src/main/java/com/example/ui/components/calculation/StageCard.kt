package com.example.ui.components.calculation

import com.example.ui.theme.ChartRed

import com.example.ui.theme.DarkBorder

import com.example.ui.theme.DarkCardAlternative

import com.example.ui.theme.DarkCard

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Rules
import com.example.utils.CalculationResult
import kotlinx.coroutines.delay

@Composable
fun StageCard(
    result: CalculationResult,
    stageNumber: Int,
    date: String,
    headerColor: Color,
    highlightAmountColor: Color
) {
    val rate = if (stageNumber == 1) Rules.getStage1Rate(result.grade) else Rules.getStage2Rate(result.grade)
    val rateInt = (rate * 100).toInt()
    val rateStr = "${rateInt}%"
    
    val newBasic = if (stageNumber == 1) result.stage1 else result.stage2
    
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = DarkCard.copy(alpha = 0.8f),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder.copy(alpha = 0.5f)),
        shadowElevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerColor.copy(alpha = 0.9f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Stage-$stageNumber • $date",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "  |  $rateStr",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                // Summary Boxes
                val increaseAmt = newBasic - result.currentBasic
                val boxesScrollState = rememberScrollState()
                
                LaunchedEffect(Unit) {
                    delay(400)
                    boxesScrollState.animateScrollTo(80)
                    delay(100)
                    boxesScrollState.animateScrollTo(0)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(boxesScrollState),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SummaryBox(
                        title1 = "OLD BASIC",
                        title2 = "(30-06-26)",
                        amount = result.currentBasic,
                        modifier = Modifier.width(110.dp).defaultMinSize(minHeight = 85.dp),
                        bgColor = DarkCardAlternative,
                        borderColor = DarkBorder,
                        amountColor = ChartRed
                    )
                    SummaryBox(
                        title1 = "MATCHED 2026",
                        title2 = "STAGE",
                        amount = result.details.nextStep2026Target,
                        modifier = Modifier.width(110.dp).defaultMinSize(minHeight = 85.dp),
                        bgColor = DarkCardAlternative,
                        borderColor = DarkBorder,
                        amountColor = Color.White
                    )
                    SummaryBox(
                        title1 = "INCREASE",
                        title2 = "($rateStr)",
                        amount = increaseAmt,
                        modifier = Modifier.width(110.dp).defaultMinSize(minHeight = 85.dp),
                        bgColor = DarkCardAlternative,
                        borderColor = DarkBorder,
                        amountColor = Color(0xFF64B5F6) // Light blue
                    )
                    SummaryBox(
                        title1 = "NEW BASIC",
                        title2 = "($date)",
                        amount = newBasic,
                        modifier = Modifier.width(110.dp).defaultMinSize(minHeight = 85.dp),
                        bgColor = headerColor.copy(alpha = 0.12f),
                        borderColor = headerColor.copy(alpha = 0.3f),
                        amountColor = highlightAmountColor
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Table
                TableSection(result, stageNumber, rateStr, rate, newBasic, date, highlightAmountColor)
            }
        }
    }
}
