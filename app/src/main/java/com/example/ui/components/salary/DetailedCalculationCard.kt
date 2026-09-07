package com.example.ui.components.salary

import androidx.compose.ui.res.stringResource
import com.example.R

import com.example.ui.theme.ChipBg

import com.example.ui.theme.DarkCard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfile

@Composable
fun DetailedCalculationCard(
    basic: Long,
    profile: UserProfile,
    allowances: Map<String, Long>
) {
    val totalGross = allowances["totalGross"] ?: 0L
    val gpf = profile.gpfDeduction

    Card(
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(id = R.string.calculation_details), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                
                val context = androidx.compose.ui.platform.LocalContext.current
                val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
                    androidx.activity.result.contract.ActivityResultContracts.CreateDocument("application/pdf")
                ) { uri ->
                    if (uri != null) {
                        try {
                            val pfd = context.contentResolver.openFileDescriptor(uri, "w")
                            if (pfd != null) {
                                java.io.FileOutputStream(pfd.fileDescriptor).use { outputStream ->
                                    com.example.utils.PdfGenerator.generateSalaryStatement(
                                        context = context,
                                        outputStream = outputStream,
                                        basic = basic,
                                        profile = profile,
                                        allowances = allowances,
                                        gradeIndex = profile.selectedGradeIndex,
                                        stepIndex = profile.selectedStepIndex,
                                        stageName = profile.selectedStageName
                                    )
                                }
                                pfd.close()
                                android.widget.Toast.makeText(context, "PDF Saved", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        } catch(e: Exception) {
                            e.printStackTrace()
                            android.widget.Toast.makeText(context, "Failed to save PDF", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                
                androidx.compose.material3.IconButton(
                    onClick = { launcher.launch("Salary_Statement_${profile.selectedStageName}.pdf") },
                    modifier = Modifier.size(36.dp).background(com.example.ui.theme.AccentPurple.copy(alpha=0.2f), RoundedCornerShape(8.dp))
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Rounded.PictureAsPdf,
                        contentDescription = "Download PDF",
                        tint = com.example.ui.theme.AccentPurpleLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            SalaryRowItem(stringResource(id = R.string.salary_basic), "৳$basic")
            SalaryRowItem(stringResource(id = R.string.profile_house_rent), "৳${allowances["houseRent"] ?: 0L}")
            SalaryRowItem(stringResource(id = R.string.salary_medical), "৳${allowances["medicalAllowance"] ?: 0L}")
            
            val educationAllowance = allowances["educationAllowance"] ?: 0L
            if (educationAllowance > 0) {
                SalaryRowItem(stringResource(id = R.string.salary_education_allowance), "৳$educationAllowance")
            }
            val tiffinAllowance = allowances["tiffinAllowance"] ?: 0L
            if (tiffinAllowance > 0) {
                SalaryRowItem(stringResource(id = R.string.profile_tiffin_allowance), "৳$tiffinAllowance")
            }
            val washingAllowance = allowances["washingAllowance"] ?: 0L
            if (washingAllowance > 0) {
                SalaryRowItem(stringResource(id = R.string.profile_washing_allowance), "৳$washingAllowance")
            }
            val frontierAllowance = allowances["frontierAllowance"] ?: 0L
            if (frontierAllowance > 0) {
                SalaryRowItem(stringResource(id = R.string.profile_frontier_allowance), "৳$frontierAllowance")
            }
            val hillAllowance = allowances["hillAllowance"] ?: 0L
            if (hillAllowance > 0) {
                SalaryRowItem("Hill Allowance", "৳$hillAllowance")
            }
            val disabledChildAllowance = allowances["disabledChildAllowance"] ?: 0L
            if (disabledChildAllowance > 0) {
                SalaryRowItem("Disabled Child Allowance", "৳$disabledChildAllowance")
            }
            val mobileBill = allowances["mobileBill"] ?: 0L
            if (mobileBill > 0) {
                SalaryRowItem("Mobile Bill", "৳$mobileBill")
            }
            val tradeAllowance = allowances["tradeAllowance"] ?: 0L
            if (tradeAllowance > 0) {
                SalaryRowItem("Trade/Special Allowance", "৳$tradeAllowance")
            }
            
            HorizontalDivider(color = ChipBg, modifier = Modifier.padding(vertical = 8.dp))
            SalaryRowItem(stringResource(id = R.string.salary_gross), "৳$totalGross", isBold = true)

            // Deductions Calculation
            
            
                        
            val revenueStamp = 10L
            
            val totalDeductions = gpf + revenueStamp
            val netSalary = totalGross - totalDeductions

            Spacer(modifier = Modifier.height(16.dp))
            Text("Deductions", color = Color(0xFFEF4444), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            
            if (gpf > 0) {
                SalaryRowItem("GPF Deduction", "-৳$gpf", isBold = false)
            }
                                    SalaryRowItem("Revenue Stamp", "-৳$revenueStamp", isBold = false)
            
            HorizontalDivider(color = ChipBg, modifier = Modifier.padding(vertical = 8.dp))
            SalaryRowItem("Total Deductions", "-৳$totalDeductions", isBold = true)
            
            HorizontalDivider(color = ChipBg, modifier = Modifier.padding(vertical = 8.dp))
            SalaryRowItem(stringResource(id = R.string.salary_net), "৳$netSalary", isBold = true)
        }
    }
}
