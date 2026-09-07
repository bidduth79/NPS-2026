package com.example.ui.components.profile

import com.example.ui.theme.TextGray
import com.example.ui.theme.AccentPurpleLight
import com.example.ui.theme.AccentPurple
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfile

@Composable
fun ProfileHouseAllowanceSection(profile: UserProfile, onProfileChange: (UserProfile) -> Unit) {
    val cardBg = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    val textColor = MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "House Rent",
                    tint = AccentPurpleLight,
                    modifier = Modifier
                        .background(AccentPurple.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("House Allowance & Regional", color = textColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            // Marital Status
            ToggleRow(
                title = "Are you Married?",
                option1 = "No",
                option2 = "Yes",
                selectedOption = if (profile.maritalStatus == "Married") "Yes" else "No",
                onOptionSelected = { opt ->
                    onProfileChange(profile.copy(maritalStatus = if (opt == "Yes") "Married" else "Unmarried"))
                }
            )

            if (profile.maritalStatus == "Married") {
                Spacer(modifier = Modifier.height(16.dp))
                // Location Type (Line Man / Family Man)
                ToggleRow(
                    title = "Location Type",
                    option1 = "Line Man",
                    option2 = "Family Man",
                    selectedOption = if (profile.isFamilyMan) "Family Man" else "Line Man",
                    onOptionSelected = { opt ->
                        val isFamily = opt == "Family Man"
                        onProfileChange(profile.copy(isLineMan = !isFamily, isFamilyMan = isFamily))
                    }
                )

                if (profile.isFamilyMan) {
                    Spacer(modifier = Modifier.height(16.dp))
                    // Living Status
                    ToggleRow(
                        title = "Are you In-living?",
                        option1 = "No",
                        option2 = "Yes",
                        selectedOption = if (profile.isInLiving) "Yes" else "No",
                        onOptionSelected = { opt ->
                            onProfileChange(profile.copy(isInLiving = opt == "Yes"))
                        }
                    )

                    if (!profile.isInLiving) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Posting Area (Out-living)", color = textColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(8.dp))
                        LocationPostingArea(profile, onProfileChange)
                    }
                }
            }
            
            HorizontalDivider(color = borderColor, modifier = Modifier.padding(vertical = 16.dp))
            
            RegionalAllowances(profile, onProfileChange)
        }
    }
}

@Composable
fun ToggleRow(
    title: String,
    option1: String,
    option2: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(50))
                .padding(2.dp)
        ) {
            val isOpt1 = selectedOption == option1
            Box(
                modifier = Modifier
                    .background(if (isOpt1) AccentPurple else Color.Transparent, RoundedCornerShape(50))
                    .clickable { onOptionSelected(option1) }
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(option1, color = if (isOpt1) Color.White else TextGray, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
            val isOpt2 = selectedOption == option2
            Box(
                modifier = Modifier
                    .background(if (isOpt2) AccentPurple else Color.Transparent, RoundedCornerShape(50))
                    .clickable { onOptionSelected(option2) }
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(option2, color = if (isOpt2) Color.White else TextGray, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
