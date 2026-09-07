package com.example.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfile
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.DarkCard
import com.example.ui.theme.TextGray

@Composable
fun ProfileChildrenCard(
    profile: UserProfile,
    onProfileChange: (UserProfile) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.School,
                    contentDescription = "Children",
                    tint = AccentPurpleLight,
                    modifier = Modifier
                        .background(AccentPurple.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Children", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    color = AccentPurple.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentPurpleLight.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "Education",
                        color = AccentPurpleLight,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Eligible for Education", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = if (profile.selectedScale == "2015") "500 ৳/child (Max 2)" else "1,000 ৳/child (Max 2)", 
                    color = AccentPurpleLight, 
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(0, 1, 2).forEach { num ->
                    val isSelected = profile.numberOfChildren == num || (num == 2 && profile.numberOfChildren > 2)
                    val text = if (num == 2) "2 or more" else num.toString()
                    
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(
                                if (isSelected) AccentPurple.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.05f),
                                RoundedCornerShape(12.dp)
                            )
                            .border(
                                1.dp,
                                if (isSelected) AccentPurpleLight else Color.Transparent,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { onProfileChange(profile.copy(numberOfChildren = num)) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            color = if (isSelected) Color.White else TextGray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Disabled Children", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("1,000 ৳ per child", color = Color(0xFF10B981), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = if (profile.numberOfDisabledChildren == 0) "" else profile.numberOfDisabledChildren.toString(),
                onValueChange = { 
                    val parsed = it.filter { char -> char.isDigit() }.toIntOrNull() ?: 0
                    onProfileChange(profile.copy(numberOfDisabledChildren = parsed)) 
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.05f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                    focusedBorderColor = AccentPurpleLight,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                placeholder = { Text("0", color = TextGray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = { Text("Children", color = TextGray, modifier = Modifier.padding(end = 16.dp)) }
            )
        }
    }
}
