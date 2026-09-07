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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
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
fun Stage3Card(
    result: CalculationResult,
    headerColor: Color,
    highlightAmountColor: Color
) {
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
                        text = "Stage-3 • 01-07-2027",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "  |  Next Increment",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                // Summary Boxes
                val boxesScrollState = rememberScrollState()
                
                LaunchedEffect(Unit) {
                    delay(400)
                    boxesScrollState.animateScrollTo(80)
                    delay(100)
                    boxesScrollState.animateScrollTo(0)
                }
                
                Row(
                    modifier = Modifier.horizontalScroll(boxesScrollState),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                        title1 = "INCREASE",
                        title2 = "(100%)",
                        amount = result.convertedBasic - result.currentBasic,
                        modifier = Modifier.width(110.dp).defaultMinSize(minHeight = 85.dp),
                        bgColor = DarkCardAlternative,
                        borderColor = DarkBorder,
                        amountColor = Color(0xFF64B5F6)
                    )
                    SummaryBox(
                        title1 = "NEW BASIC",
                        title2 = "(01-07-27)",
                        amount = result.convertedBasic,
                        modifier = Modifier.width(110.dp).defaultMinSize(minHeight = 85.dp),
                        bgColor = headerColor.copy(alpha = 0.12f),
                        borderColor = headerColor.copy(alpha = 0.3f),
                        amountColor = highlightAmountColor
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Table (similar to TableSection)
                val tableScrollState = rememberScrollState()
                
                LaunchedEffect(Unit) {
                    delay(400)
                    tableScrollState.animateScrollTo(100)
                    delay(100)
                    tableScrollState.animateScrollTo(0)
                }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(tableScrollState)
                ) {
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Max).defaultMinSize(minWidth = 460.dp)
                    ) {
                        // Table Header
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
                        TableRow("1", "Fixed Basic on 01-07-2026", "-", result.convertedBasic)
                        // Step 2
                        val nextStageIncrement = result.stage3 - result.convertedBasic
                        TableRow("2", "Annual Increment (01-07-2027)", "Next step difference", nextStageIncrement)
                        // Step 3
                        TableRow("3", "Basic with Increment (01-07-2027)", "${NumberFormatter.format(result.convertedBasic)} + ${NumberFormatter.format(nextStageIncrement)}", result.stage3)
                    }
                }
            }
        }
    }
}
