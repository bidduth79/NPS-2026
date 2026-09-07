import re

with open('app/src/main/java/com/example/ui/components/Selectors.kt', 'r') as f:
    content = f.read()

# Fix text colors in Selectors
content = content.replace('color = if (selectedIndex >= 0) Color.White else Color.White.copy(alpha = 0.5f)', 
                          'color = if (selectedIndex >= 0) androidx.compose.material3.MaterialTheme.colorScheme.onSurface else androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)')
content = content.replace('color = if (index == selectedIndex) AccentPurpleLight else Color.White',
                          'color = if (index == selectedIndex) AccentPurpleLight else androidx.compose.material3.MaterialTheme.colorScheme.onSurface')

with open('app/src/main/java/com/example/ui/components/Selectors.kt', 'w') as f:
    f.write(content)
