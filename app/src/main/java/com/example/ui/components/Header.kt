package com.example.ui.components

import com.example.ui.theme.DeepDarkCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BluePrimary

@Composable
fun AppHeader(scrollOffset: Int = 0, onMenuClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(DeepDarkCard) // Deep dark purple background
    ) {
        // Decorative background circle with slight parallax
        Box(
            modifier = Modifier
                .offset(x = 250.dp, y = ((-50) - (scrollOffset * 0.1f)).dp)
                .size(200.dp)
                .background(androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), CircleShape)
        )
        
        // 3D Icons placed in the background with parallax effect
        HeroIllustration(
            scrollOffset = scrollOffset,
            modifier = Modifier.matchParentSize()
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Welcome! 👋",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Pay Scale 2026",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Menu Button and Theme Toggle
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ThemeToggle()
                    IconButton(
                        onClick = onMenuClick,
                        modifier = Modifier
                            .size(48.dp)
                            .background(androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            
            Text(
                text = "Select your grade and step to instantly\ncalculate your new salary.",
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 18.sp
            )
        }
    }
}
