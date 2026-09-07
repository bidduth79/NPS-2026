with open("app/src/main/java/com/example/ui/components/CalculationDetails.kt", "r") as f:
    content = f.read()

import re

stage3_replacement = """
@Composable
private fun Stage3Card(
    result: CalculationResult,
    headerColor: Color,
    highlightAmountColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1C1924).copy(alpha = 0.8f),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B3247).copy(alpha = 0.5f)),
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
                val nextStageIncrement = result.stage3 - result.details.nextStep2026Target
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
                        title2 = "(01-07-26)",
                        amount = result.details.nextStep2026Target,
                        modifier = Modifier.width(110.dp).defaultMinSize(minHeight = 85.dp),
                        bgColor = Color(0xFF252131),
                        borderColor = Color(0xFF3B3247),
                        amountColor = Color(0xFFEF5350)
                    )
                    SummaryBox(
                        title1 = "INCREMENT",
                        title2 = "(Next Stage)",
                        amount = nextStageIncrement,
                        modifier = Modifier.width(110.dp).defaultMinSize(minHeight = 85.dp),
                        bgColor = Color(0xFF252131),
                        borderColor = Color(0xFF3B3247),
                        amountColor = Color(0xFF64B5F6)
                    )
                    SummaryBox(
                        title1 = "NEW BASIC",
                        title2 = "(01-07-27)",
                        amount = result.stage3,
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
                        
                        HorizontalDivider(color = Color(0xFF3B3247))
                        
                        // Step 1
                        TableRow("1", "Basic on 01-07-2026", "-", result.details.nextStep2026Target)
                        // Step 2
                        TableRow("2", "Next Stage Increment", "Next stage difference", nextStageIncrement)
                        // Step 3
                        TableRow("3", "New Basic (01-07-2027)", "${NumberFormatter.format(result.details.nextStep2026Target)} + ${NumberFormatter.format(nextStageIncrement)}", result.stage3)
                    }
                }
            }
        }
    }
}
"""

content = re.sub(r"@Composable\nprivate fun Stage3Card\([\s\S]*?\}\n\}\n", stage3_replacement, content)

with open("app/src/main/java/com/example/ui/components/CalculationDetails.kt", "w") as f:
    f.write(content)
