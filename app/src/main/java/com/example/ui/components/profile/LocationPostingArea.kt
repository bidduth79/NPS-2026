package com.example.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
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
import com.example.ui.theme.TextGray

@Composable
fun LocationPostingArea(profile: UserProfile, onProfileChange: (UserProfile) -> Unit) {
    val locations = listOf(
        "Dhaka Metropolitan",
        "Other City Corporation",
        "Other Locations"
    )
    Column {
        locations.forEach { loc ->
            val isSelected = profile.locationType == loc
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        if (isSelected) AccentPurple.copy(alpha = 0.15f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        if (isSelected) AccentPurpleLight else Color.Transparent,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { onProfileChange(profile.copy(locationType = loc)) }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = loc,
                        color = if (isSelected) MaterialTheme.colorScheme.onSurface else TextGray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                    if (isSelected) {
                        Icon(
                            Icons.Rounded.Home, 
                            contentDescription = "Selected", 
                            tint = AccentPurpleLight, 
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
