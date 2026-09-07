with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'r') as f:
    content = f.read()

import re

# Remove any duplicated imports 
lines = content.split('\n')
unique_imports = set()
cleaned_lines = []

for line in lines:
    if line.startswith('import '):
        if line not in unique_imports:
            unique_imports.add(line)
            cleaned_lines.append(line)
    else:
        cleaned_lines.append(line)

content = '\n'.join(cleaned_lines)

# Ensure setValue/getValue imports exist for mutableFloatStateOf
required_imports = """import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.DisposableEffect
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
"""

# add after package
content = content.replace('package com.example.ui.components.navigation\n', 'package com.example.ui.components.navigation\n' + required_imports)

# Remove nested/mid-file imports
content = re.sub(r'\nimport android\.content\.Context\nimport android\.hardware\.Sensor.*?\n\n', '\n\n', content, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'w') as f:
    f.write(content)

