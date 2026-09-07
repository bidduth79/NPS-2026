import os
import re

dir_path = 'app/src/main/java/com/example/ui'

replacements = {
    # Replace the private val TextPrimary = Color.White
    r'private val TextPrimary = Color\.White': 'private val TextPrimary\n    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurface',
    r'private val TextSecondary = Color\.LightGray': 'private val TextSecondary\n    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant',
    r'private val TextSecondary = Color\.Gray': 'private val TextSecondary\n    @androidx.compose.runtime.Composable get() = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant',
    
    # We will modify Color.kt separately using edit_file
}

for root, _, files in os.walk(dir_path):
    for file in files:
        if file.endswith('.kt'):
            path = os.path.join(root, file)
            with open(path, 'r') as f:
                content = f.read()
            
            new_content = content
            for old, new in replacements.items():
                new_content = re.sub(old, new, new_content)
                
            if new_content != content:
                with open(path, 'w') as f:
                    f.write(new_content)
                print(f"Updated {path}")
