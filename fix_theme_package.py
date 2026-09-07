with open('app/src/main/java/com/example/ui/theme/Theme.kt', 'r') as f:
    content = f.read()
content = content.replace("import androidx.compose.runtime.staticCompositionLocalOfpackage com.example.ui.theme", "package com.example.ui.theme\nimport androidx.compose.runtime.staticCompositionLocalOf")
with open('app/src/main/java/com/example/ui/theme/Theme.kt', 'w') as f:
    f.write(content)
