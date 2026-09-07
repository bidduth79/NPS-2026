import re
with open('app/src/main/java/com/example/ui/components/profile/LocationPostingArea.kt', 'r') as f:
    content = f.read()

content = content.replace('import androidx.compose.material3.Text', 'import androidx.compose.material3.Text\nimport androidx.compose.material3.MaterialTheme')

content = content.replace('Color.White.copy(alpha = 0.05f)', 'MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)')
content = content.replace('Color.White', 'MaterialTheme.colorScheme.onSurface')

with open('app/src/main/java/com/example/ui/components/profile/LocationPostingArea.kt', 'w') as f:
    f.write(content)
