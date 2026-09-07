import re

with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'r') as f:
    content = f.read()

# Replace hardcoded Color.White in NavigationComponents
content = content.replace('val unselectedColor = Color.White', 'val unselectedColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)')
content = content.replace('tint = Color.White', 'tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface')
content = content.replace('color = Color.White', 'color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface')
content = content.replace('tint = Color(0xFF121212)', 'tint = androidx.compose.material3.MaterialTheme.colorScheme.surface')

with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'w') as f:
    f.write(content)
