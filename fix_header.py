import re

with open('app/src/main/java/com/example/ui/components/Header.kt', 'r') as f:
    content = f.read()

content = content.replace('Color.White', 'androidx.compose.material3.MaterialTheme.colorScheme.onSurface')

with open('app/src/main/java/com/example/ui/components/Header.kt', 'w') as f:
    f.write(content)
