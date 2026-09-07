with open("app/src/main/java/com/example/ui/components/CustomBottomNavigation.kt", "r") as f:
    content = f.read()

replacement = """package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BluePrimary

@Composable
fun CustomBottomNavigation(scrollState: ScrollState, modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(true) }
    var previousScroll by remember { mutableIntStateOf(0) }
    
    // Maintain selected state for interactivity
    var selectedItem by remember { mutableIntStateOf(0) }

    LaunchedEffect(scrollState.value) {
        val currentScroll = scrollState.value
        val delta = currentScroll - previousScroll
        if (currentScroll == 0) {
            isVisible = true
        } else if (delta > 15) {
            isVisible = false
        } else if (delta < -15) {
            isVisible = true
        }
        previousScroll = currentScroll
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it * 2 }),
        exit = slideOutVertically(targetOffsetY = { it * 2 }),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp) // Total height to accommodate the floating button
        ) {
            // Background bar (Flush with the bottom)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color(0xFF0F0F11)) // Dark background like the uploaded image
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NavMenuItem(
                        icon = Icons.Rounded.Home, 
                        label = "Home", 
                        isSelected = selectedItem == 0,
                        onClick = { selectedItem = 0 }
                    )
                    NavMenuItem(
                        icon = Icons.Rounded.Article, 
                        label = "Gazette", 
                        isSelected = selectedItem == 1,
                        onClick = { selectedItem = 1 }
                    )
                    
                    // Spacer for the center button
                    Spacer(modifier = Modifier.width(72.dp))
                    
                    NavMenuItem(
                        icon = Icons.Rounded.AccountBalanceWallet, 
                        label = "Salary", 
                        isSelected = selectedItem == 2,
                        onClick = { selectedItem = 2 }
                    )
                    NavMenuItem(
                        icon = Icons.Rounded.MoreHoriz, 
                        label = "More", 
                        isSelected = selectedItem == 3,
                        onClick = { selectedItem = 3 }
                    )
                }
            }
            
            // Floating Center Button
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE5E7EB)) // Light gray/white like the image
                    .clickable { /* Handle click */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Dashboard,
                    contentDescription = "Menu",
                    tint = Color(0xFF121212),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun RowScope.NavMenuItem(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
    val selectedColor = Color(0xFFF83648) // Reddish highlight color
    val unselectedColor = Color.White
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) selectedColor else unselectedColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (isSelected) selectedColor else unselectedColor,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
"""

with open("app/src/main/java/com/example/ui/components/CustomBottomNavigation.kt", "w") as f:
    f.write(replacement)
