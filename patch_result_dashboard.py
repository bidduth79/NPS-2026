with open('app/src/main/java/com/example/ui/components/ResultDashboard.kt', 'r') as f:
    content = f.read()

# Fix flip logic
content = content.replace(
    'val displayedStage = activeStage',
    'val currentSide = Math.round(rotation / 180f)\n    val displayedStage = (currentSide % 4).let { if (it < 0) it + 4 else it }'
)

content = content.replace(
    'val currentSide = (rotation / 180f).toInt()\n                    rotationY = if (currentSide % 2 != 0) 180f else 0f',
    'rotationY = if (currentSide % 2 != 0) 180f else 0f'
)

# Add indicator icon
target_icon = '''                com.example.ui.components.dashboard.DashboardSubAmounts(state, isCompact, displayedStage)
            }
        }'''

replacement_icon = '''                com.example.ui.components.dashboard.DashboardSubAmounts(state, isCompact, displayedStage)
            }
            
            if (onClick != null) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Next",
                    tint = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(16.dp)
                )
            }
        }'''

content = content.replace(target_icon, replacement_icon)

# Make sure imports are present
if 'import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos' not in content:
    content = content.replace(
        'import androidx.compose.material.icons.filled.ArrowUpward',
        'import androidx.compose.material.icons.filled.ArrowUpward\nimport androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos'
    )

with open('app/src/main/java/com/example/ui/components/ResultDashboard.kt', 'w') as f:
    f.write(content)
