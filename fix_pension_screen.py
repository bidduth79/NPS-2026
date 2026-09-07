with open('app/src/main/java/com/example/ui/components/pension/PensionScreen.kt', 'r') as f:
    content = f.read()

import re

# Remove the result card block and breakdown block
# We will use regex to find the specific Card blocks or just string replace because it's safer if done carefully.

result_card_block = """            // Result Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = GreenSuccess.copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, GreenSuccess.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Rounded.AccountBalance, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Net Monthly Pension", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                    Text(
                        "৳ ${NumberFormatter.format(netMonthlyPension.toLong())}",
                        color = GreenSuccess,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (isSurrenderHalf) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(color = GreenSuccess.copy(alpha = 0.2f))
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text("Lump Sum Gratuity (এককালীন)", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        Text(
                            "৳ ${NumberFormatter.format(totalLumpSum.toLong())}",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Detailed Breakdown
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Calculation Breakdown", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    BreakdownRow("Gross Pension", "৳ ${NumberFormatter.format(grossPension.toLong())}")
                    BreakdownRow("Surrendered Amount", "৳ ${NumberFormatter.format(surrenderedAmount.toLong())}")
                    BreakdownRow("Retained Pension", "৳ ${NumberFormatter.format(retainedPension.toLong())}")
                    BreakdownRow("Medical Allowance", "৳ ${NumberFormatter.format(medicalAllowance.toLong())}")
                    Divider(color = DarkBorder, modifier = Modifier.padding(vertical = 12.dp))
                    BreakdownRow("Total Monthly", "৳ ${NumberFormatter.format(netMonthlyPension.toLong())}", isBold = true, color = GreenSuccess)
                }
            }"""

replacement = """            // Result Card
            PensionResultCard(
                netMonthlyPension = netMonthlyPension,
                isSurrenderHalf = isSurrenderHalf,
                totalLumpSum = totalLumpSum
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Detailed Breakdown
            PensionBreakdownCard(
                grossPension = grossPension,
                surrenderedAmount = surrenderedAmount,
                retainedPension = retainedPension,
                medicalAllowance = medicalAllowance,
                netMonthlyPension = netMonthlyPension
            )"""

content = content.replace(result_card_block, replacement)

# Remove BreakdownRow function from the bottom
breakdown_func = """@Composable
fun BreakdownRow(label: String, value: String, isBold: Boolean = false, color: Color = Color.White) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label, 
            color = if (isBold) color else Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            value, 
            color = color,
            fontSize = 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium
        )
    }
}"""

content = content.replace(breakdown_func, "")

with open('app/src/main/java/com/example/ui/components/pension/PensionScreen.kt', 'w') as f:
    f.write(content)
