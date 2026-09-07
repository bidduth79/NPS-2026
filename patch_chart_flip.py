with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'r') as f:
    content = f.read()

# Fix flip logic
content = content.replace(
    'var flipCount by remember { mutableIntStateOf(0) }',
    'var targetFlipCount by remember { mutableIntStateOf(0) }'
)

content = content.replace(
    'targetValue = flipCount * 180f,',
    'targetValue = targetFlipCount * 180f,'
)

content = content.replace(
    'val displayIndex = flipCount % totalStates\n    val contentRotation = if (flipCount % 2 != 0) 180f else 0f',
    'val currentSide = Math.round(rotation / 180f)\n    val displayIndex = (currentSide % totalStates).let { if (it < 0) it + totalStates else it }\n    val contentRotation = if (currentSide % 2 != 0) 180f else 0f'
)

content = content.replace(
    'flipCount++',
    'targetFlipCount++'
)

# Add indicator icon
target_box = '''        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isCompact) 12.dp else 20.dp)
                .graphicsLayer {
                    rotationY = contentRotation
                }
        ) {
            ChartFlipContent('''

replacement_box = '''        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isCompact) 12.dp else 20.dp)
                .graphicsLayer {
                    rotationY = contentRotation
                }
        ) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = "Next",
                tint = Color.White.copy(alpha = 0.2f),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(16.dp)
            )

            ChartFlipContent('''

content = content.replace(target_box, replacement_box)

# Add import for Icons if not present (it seems not all icons are imported)
if 'import androidx.compose.material.icons.Icons' not in content:
    content = content.replace(
        'import androidx.compose.material3.HorizontalDivider',
        'import androidx.compose.material3.HorizontalDivider\nimport androidx.compose.material.icons.Icons\nimport androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos\nimport androidx.compose.material3.Icon'
    )

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'w') as f:
    f.write(content)
