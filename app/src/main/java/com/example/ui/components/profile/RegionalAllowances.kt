package com.example.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfile
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.TextGray

@Composable
fun RegionalAllowances(profile: UserProfile, onProfileChange: (UserProfile) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        // Shimanto
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Column {
                Text("Shimanto Allowance", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Enable", color = TextGray, fontSize = 14.sp)
                    Switch(
                        checked = profile.hasFrontierAllowance,
                        onCheckedChange = { onProfileChange(profile.copy(hasFrontierAllowance = it)) },
                        modifier = Modifier.scale(0.8f),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AccentPurpleLight,
                            uncheckedThumbColor = TextGray,
                            uncheckedTrackColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                }
                
                if (profile.hasFrontierAllowance) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = profile.joiningDate,
                        onValueChange = { onProfileChange(profile.copy(joiningDate = it)) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedBorderColor = AccentPurpleLight,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        placeholder = { Text("DD/MM/YYYY", color = TextGray, fontSize = 12.sp) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
            }
        }

        // Hill Allowance
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Column {
                Text("Hill Allowance", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Enable", color = TextGray, fontSize = 14.sp)
                    Switch(
                        checked = profile.hasHillAllowance,
                        onCheckedChange = { onProfileChange(profile.copy(hasHillAllowance = it)) },
                        modifier = Modifier.scale(0.8f),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AccentPurpleLight,
                            uncheckedThumbColor = TextGray,
                            uncheckedTrackColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                }
                
                if (profile.hasHillAllowance) {
                    Spacer(modifier = Modifier.height(12.dp))
                    val areas = listOf("Sadar", "Other")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        areas.forEach { area ->
                            val isSelected = profile.hillAllowanceAreaType == area
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(36.dp)
                                    .background(
                                        if (isSelected) AccentPurple.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.05f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) AccentPurpleLight else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onProfileChange(profile.copy(hillAllowanceAreaType = area)) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = area,
                                    color = if (isSelected) Color.White else TextGray,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
