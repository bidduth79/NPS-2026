import re
import os

dir_path = 'app/src/main/java/com/example/ui'

for root, _, files in os.walk(dir_path):
    for file in files:
        if file.endswith('.kt'):
            path = os.path.join(root, file)
            with open(path, 'r') as f:
                content = f.read()
            
            new_content = re.sub(r'private val (\w+) = (DarkCardInner|DarkBorder\.copy\(alpha = 0\.5f\)|DarkCard)', r'private val \1\n    @androidx.compose.runtime.Composable get() = \2', content)
            
            if new_content != content:
                with open(path, 'w') as f:
                    f.write(new_content)
                print(f"Updated {path}")
