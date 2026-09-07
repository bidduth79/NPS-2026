package com.example.ui.components.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.WorkOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfile
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.DarkCard

@Composable
fun ProfileOtherAllowancesCard(
    profile: UserProfile,
    onProfileChange: (UserProfile) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.WorkOutline,
                    contentDescription = "Other Allowances",
                    tint = AccentPurpleLight,
                    modifier = Modifier
                        .background(AccentPurple.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Other Allowances", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    color = AccentPurple.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentPurpleLight.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "Tiffin & Bills",
                        color = AccentPurpleLight,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tiffin Allowance
            ToggleRow(
                title = "Tiffin Allowance",
                subtitle = if (profile.selectedScale == "2015") "200 ৳/month (2015 Scale)" else "600 ৳/month (2026 Scale)",
                isChecked = profile.hasTiffinAllowance,
                onCheckedChange = { onProfileChange(profile.copy(hasTiffinAllowance = it)) }
            )

            HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(vertical = 12.dp))

            // Washing Allowance
            ToggleRow(
                title = "Washing / Barber Allowance",
                subtitle = "150 ৳/month",
                isChecked = profile.hasWashingAllowance,
                onCheckedChange = { onProfileChange(profile.copy(hasWashingAllowance = it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mobile & Trade Bills
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AmountInputBox(
                    modifier = Modifier.weight(1f),
                    title = "Mobile Bill",
                    amount = profile.mobileBillAmount,
                    onAmountChange = { onProfileChange(profile.copy(mobileBillAmount = it)) }
                )
                AmountInputBox(
                    modifier = Modifier.weight(1f),
                    title = "Trade Allowance",
                    amount = profile.tradeAllowanceAmount,
                    onAmountChange = { onProfileChange(profile.copy(tradeAllowanceAmount = it)) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Expandable Section (More / Less)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(if (expanded) "Less" else "More", color = AccentPurpleLight, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Expand",
                        tint = AccentPurpleLight,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(bottom = 16.dp))
                    
                    // Festival Allowance
                    ToggleRow(
                        title = "Festival Allowance",
                        subtitle = "Equal to Basic Salary",
                        isChecked = profile.hasFestivalAllowance,
                        onCheckedChange = { onProfileChange(profile.copy(hasFestivalAllowance = it)) }
                    )

                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(vertical = 12.dp))

                    // Baishakhi Allowance
                    ToggleRow(
                        title = "Baishakhi Allowance",
                        subtitle = "20% of Basic Salary",
                        isChecked = profile.hasBaishakhiAllowance,
                        onCheckedChange = { onProfileChange(profile.copy(hasBaishakhiAllowance = it)) }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    // Recreation & Award Allowances
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AmountInputBox(
                            modifier = Modifier.weight(1f),
                            title = "Recreation Allowance",
                            amount = profile.recreationAllowanceAmount,
                            onAmountChange = { onProfileChange(profile.copy(recreationAllowanceAmount = it)) }
                        )
                        AmountInputBox(
                            modifier = Modifier.weight(1f),
                            title = "Award Allowance",
                            amount = profile.awardAllowanceAmount,
                            onAmountChange = { onProfileChange(profile.copy(awardAllowanceAmount = it)) }
                        )
                    }
                }
            }
        }
    }
}
