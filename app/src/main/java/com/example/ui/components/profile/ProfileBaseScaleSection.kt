package com.example.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.TextGray

@Composable
fun ProfileBaseScaleSection(
    profile: UserProfile,
    onProfileChange: (UserProfile) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Base Scale Card
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCard),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.AccountBalance,
                        contentDescription = "Base Scale",
                        tint = AccentPurpleLight,
                        modifier = Modifier
                            .background(AccentPurple.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .padding(8.dp)
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Base Scale", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        color = AccentPurple.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentPurpleLight.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = "Base",
                            color = AccentPurpleLight,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Select applicable pay scale", color = TextGray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ScaleOptionCard(
                        modifier = Modifier.weight(1f),
                        title = "2015 Scale",
                        subtitle = "Previous rules & rates",
                        isSelected = profile.selectedScale == "2015",
                        onClick = { onProfileChange(profile.copy(selectedScale = "2015")) }
                    )
                    ScaleOptionCard(
                        modifier = Modifier.weight(1f),
                        title = "2026 Scale",
                        subtitle = "Proposed new rates",
                        isSelected = profile.selectedScale == "2026",
                        onClick = { onProfileChange(profile.copy(selectedScale = "2026")) }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Info, contentDescription = "Info", tint = TextGray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (profile.selectedScale == "2015") 
                            "2015 Scale: Allowances calculated using standard 2015 government pay scale rules and previous rates." 
                        else 
                            "2026 Scale: Allowances calculated using proposed 2026 government pay scale rules and rates.",
                        color = TextGray,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
        
        // Extracted Children Card
        ProfileChildrenCard(profile = profile, onProfileChange = onProfileChange)
    }
}
